package id.careerfund.api.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import id.careerfund.api.domains.EIdVerificationStatus;
import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.entities.OneTimePassword;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.reqres.AssessmentScore;
import id.careerfund.api.domains.models.reqres.UpdateUser;
import id.careerfund.api.domains.models.requests.*;
import id.careerfund.api.domains.models.responses.FileUrlResponse;
import id.careerfund.api.domains.models.responses.MyInterests;
import id.careerfund.api.domains.models.responses.MyProfile;
import id.careerfund.api.domains.models.responses.TokenResponse;
import id.careerfund.api.repositories.InterestRepository;
import id.careerfund.api.repositories.RoleRepository;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.mappers.RoleMapper;
import id.careerfund.api.utils.mappers.UserMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Lazy
    private final UserRepository userRepo;
    @Lazy
    private final RoleRepository roleRepo;
    @Lazy
    private final InterestRepository interestRepo;
    @Lazy
    private final PasswordEncoder passwordEncoder;
    @Lazy
    private final BalanceService balanceService;
    @Lazy
    private final TokenService tokenService;
    @Lazy
    private final EmailService emailService;
    @Lazy
    private final OneTimePasswordService oneTimePasswordService;
    @Lazy
    private final StorageService storageService;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            log.error("Email not found {}", email);
            throw new UsernameNotFoundException(String.format("User not found %s", email));
        } else {
            log.info("Email found {}", email);
        }
        return user;
    }

    @Override
    public void registerUser(UserRegister userRegister) throws Exception {
        User user = UserMapper.userRegisterToUser(userRegister);
        if (!getIsEmailAvailable(user.getEmail()))
            throw new Exception("EMAIL_UNAVAILABLE");
        saveUser(user);
        addRoleToUser(user.getEmail(), RoleMapper.mapRole(userRegister.getRole()));
        addRoleToUser(user.getEmail(), ERole.ROLE_USER);
        balanceService.addBalanceToUser(user);
        sendVerificationEmail(user.getEmail());
    }

    @Override
    public User getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepo.findByEmail(email);
    }

    // @Override
    // public List<User> getUsers() {
    // log.info("Fetching all users");
    // return userRepo.findAll();
    // }

    @Override
    public boolean getIsEmailAvailable(String email) {
        return getUser(email) == null;
    }

    // @Override
    // public Interest addInterest(User user, Long id) {
    // Collection<Interest> userInterests = user.getInterests();
    // Optional<Interest> interest = interestRepo.findById(id);
    // if (!interest.isPresent()) return null;
    // if (isUserHasInterest(user, interest.get())) return null;
    // userInterests.add(interest.get());
    // return interest.get();
    // }
    //
    // @Override
    // public Interest deleteInterest(User user, Long id) {
    // Collection<Interest> userInterests = user.getInterests();
    // Interest interest = interestRepo.getById(id);
    // if (!isUserHasInterest(user, interest)) return null;
    // userInterests.remove(interest);
    // return interest;
    // }

    // @Override
    // public void addInterests(Principal principal, UpdateInterest updateInterest)
    // {
    // User user = getUser(principal.getName());
    // for (Long i : updateInterest.getInterestIds()) {
    // addInterest(user, i);
    // }
    // }
    //
    // @Override
    // public void deleteInterests(Principal principal, UpdateInterest
    // updateInterest) {
    // User user = getUser(principal.getName());
    // for (Long i : updateInterest.getInterestIds()) {
    // deleteInterest(user, i);
    // }
    // }

    @Override
    public MyInterests getMyInterests(Principal principal) {
        User user = getUser(principal.getName());
        return new MyInterests(user.getId(), (List<Interest>) user.getInterests());
    }

    @Override
    public UpdateInterest saveInterests(Principal principal, UpdateInterest updateInterest) {
        User user = getUser(principal.getName());
        List<Interest> interests = updateInterest.getInterestIds().stream().map(interestRepo::getById)
                .collect(Collectors.toList());
        log.info("Saving interests {}", interests);
        user.setInterests(interests);
        return updateInterest;
    }

    @Override
    public Boolean isUserHasInterest(User user, Interest interest) {
        return user.getInterests().contains(interest);
    }

    @Override
    public void sendVerificationEmail(EmailRequest emailRequest) throws MessagingException, NotFoundException {
        User user = getUser(emailRequest.getEmail());
        if (user == null)
            throw new NotFoundException("USER_NOT_FOUND");
        String otp = oneTimePasswordService.generateOtp(user);
        emailService.sendVerificationEmail(user, otp);
    }

    @Override
    public void sendResetPasswordEmail(EmailRequest emailRequest) throws MessagingException, NotFoundException {
        User user = getUser(emailRequest.getEmail());
        if (user == null)
            throw new NotFoundException("USER_NOT_FOUND");
        String token = oneTimePasswordService.getResetPasswordToken(user);
        emailService.sendResetPasswordEmail(user, token);
    }

    @Override
    public void resetPassword(ResetPassword resetPassword) throws NotFoundException {
        User user = oneTimePasswordService.verifyResetPasswordToken(resetPassword.getToken());
        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        oneTimePasswordService.deleteResetPasswordToken(user);
    }

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) throws Exception {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        User user = userRepo.findByEmail(email);

        if (user == null)
            throw new NotFoundException("USER_NOT_FOUND");
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException("PASSWORD_WRONG");

        Exception exception = tokenService.authUser(email, password);
        if (exception instanceof DisabledException) {
            sendVerificationEmail(email);
            return null;
        } else if (exception != null) {
            throw exception;
        }

        return tokenService.getToken(user);
    }

    @Override
    public void changePassword(Principal principal, PasswordChangeRequest passwordChangeRequest) throws BadCredentialsException {
        User userPrincipal = UserMapper.principalToUser(principal);
        User user = userRepo.getById(userPrincipal.getId());
        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword()))
            throw new BadCredentialsException("OLD_PASSWORD_WRONG");
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
    }

    private void sendVerificationEmail(String email) throws MessagingException, NotFoundException {
        sendVerificationEmail(new EmailRequest(email));
    }

    @Override
    public TokenResponse verifyUser(OtpRequest otpRequest) throws NotFoundException, TokenExpiredException {
        OneTimePassword oneTimePassword = oneTimePasswordService.verifyOtp(otpRequest.getCode());
        User user = oneTimePassword.getUser();
        user.setIsEnabled(true);
        return tokenService.getToken(user);
    }

    @Override
    public UpdateUser getProfileUpdate(Principal principal) {
        return UserMapper.principalToUpdateUser(principal);
    }

    @Override
    public UpdateUser updateUser(Principal principal, UpdateUser updateUser) {
        User userPrincipal = UserMapper.principalToUser(principal);
        User user = userRepo.getById(userPrincipal.getId());
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        user.setAddress(updateUser.getAddress());
        return UserMapper.userToUpdateUser(user);
    }

    @Override
    public MyProfile getMyProfile(Principal principal) {
        return UserMapper.principalToMyProfile(principal);
    }

    @Override
    public FileUrlResponse uploadPhoto(Principal principal, MultipartFile file) {
        User principalUser = UserMapper.principalToUser(principal);
        User user = userRepo.getById(principalUser.getId());

        String photoPath = storageService.uploadFile(file, "images/profile/");
        user.setPhotoPath(photoPath);
        return new FileUrlResponse(photoPath);
    }

    @Override
    public FileUrlResponse uploadIdentityCard(Principal principal, MultipartFile file) {
        User principalUser = UserMapper.principalToUser(principal);
        User user = userRepo.getById(principalUser.getId());

        String identityCardPath = storageService.uploadFile(file, "images/ic/");
        user.setIdentityCardPath(identityCardPath);
        return new FileUrlResponse(identityCardPath);
    }

    @Override
    public FileUrlResponse uploadSelfie(Principal principal, MultipartFile file) {
        User principalUser = UserMapper.principalToUser(principal);
        User user = userRepo.getById(principalUser.getId());

        String selfiePath = storageService.uploadFile(file, "images/selfies/");
        user.setSelfiePath(selfiePath);
        return new FileUrlResponse(selfiePath);
    }

    @Override
    public AssessmentScore saveAssessmentScore(Principal principal, AssessmentScore assessmentScore) {
        User principalUser = UserMapper.principalToUser(principal);
        User user = userRepo.getById(principalUser.getId());
        user.setAssessmentScore(assessmentScore.getScore());
        return new AssessmentScore(assessmentScore.getScore());
    }

    @Override
    public AssessmentScore getAssessmentScore(Principal principal) {
        User user = UserMapper.principalToUser(principal);
        return new AssessmentScore(user.getAssessmentScore());
    }

    @Override
    public void verifyUserId(IdRequest idRequest) {
        User user = userRepo.getById(idRequest.getId());
        if (idRequest.getStatus() == EIdVerificationStatus.VERIFIED)
            user.setIdVerificationStatus(EIdVerificationStatus.VERIFIED);
        else
            user.setIdVerificationStatus(EIdVerificationStatus.REJECTED);
    }

    @Override
    public List<User> getWaitingForVerificationUser() {
        return userRepo.findByIdVerificationStatusIs(EIdVerificationStatus.VERIFYING);
    }

    @Override
    public void requestVerify(Principal principal) {
        User principalUser = UserMapper.principalToUser(principal);
        User user = userRepo.getById(principalUser.getId());
        user.setIdVerificationStatus(EIdVerificationStatus.VERIFYING);
    }

    private void saveUser(User user) {
        log.info("Saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    private void addRoleToUser(String email, ERole roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = getUser(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
}
