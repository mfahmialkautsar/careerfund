package id.careerfund.api.services;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.*;
import id.careerfund.api.repositories.InterestRepository;
import id.careerfund.api.repositories.RoleRepository;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.mappers.RoleMapper;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final InterestRepository interestRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

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
    public TokenResponse registerUser(UserRegister userRegister) throws Exception {
        User user = UserMapper.userRegisterToUser(userRegister);
        if (!getIsEmailAvailable(user.getEmail())) throw new Exception("EMAIL_UNAVAILABLE");
        saveUser(user);
        addRoleToUser(user.getEmail(), RoleMapper.mapRole(userRegister.getRole()));
        addRoleToUser(user.getEmail(), ERole.ROLE_USER);
        return tokenService.signIn(new SignInRequest(userRegister.getEmail(), userRegister.getPassword()));
    }

    @Override
    public void registerUserIfNotExists(UserRegister userRegister) {
        if (getIsEmailAvailable(userRegister.getEmail())) {
            try {
                registerUser(userRegister);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerAdminIfNotExists(User user) {
        if (getIsEmailAvailable(user.getEmail())) {
            try {
                saveUser(user);
                addRoleToUser(user.getEmail(), ERole.ROLE_ADMIN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public Role saveRoleIfNotExists(Role role) {
        if (roleRepo.findByName(role.getName()) == null) {
            return saveRole(role);
        }
        return null;
    }

    @Override
    public User getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public boolean getIsEmailAvailable(String email) {
        return getUser(email) == null;
    }

    @Override
    public Interest addInterest(User user, Long id) {
        Collection<Interest> userInterests = user.getInterests();
        Optional<Interest> interest = interestRepo.findById(id);
        if (!interest.isPresent()) return null;
        if (isUserHasInterest(user, interest.get())) return null;
        userInterests.add(interest.get());
        return interest.get();
    }

    @Override
    public Interest deleteInterest(User user, Long id) {
        Collection<Interest> userInterests = user.getInterests();
        Interest interest = interestRepo.getById(id);
        if (!isUserHasInterest(user, interest)) return null;
        userInterests.remove(interest);
        return interest;
    }

    @Override
    public void addInterests(Principal principal, UpdateInterest updateInterest) {
        User user = getUser(principal.getName());
        for (Long i : updateInterest.getInterestIds()) {
            addInterest(user, i);
        }
    }

    @Override
    public void deleteInterests(Principal principal, UpdateInterest updateInterest) {
        User user = getUser(principal.getName());
        for (Long i : updateInterest.getInterestIds()) {
            deleteInterest(user, i);
        }
    }

    @Override
    public MyInterests getMyInterests(Principal principal) {
        User user = getUser(principal.getName());
        return new MyInterests(user.getId(), (List<Interest>) user.getInterests());
    }

    @Override
    public UpdateInterest saveInterests(Principal principal, UpdateInterest updateInterest) {
        User user = getUser(principal.getName());
        List<Interest> interests = updateInterest.getInterestIds().stream().map(interestRepo::getById).collect(Collectors.toList());
        log.info("Saving interests {}", interests);
        user.setInterests(interests);
        return updateInterest;
    }

    @Override
    public Boolean isUserHasInterest(User user, Interest interest) {
        return user.getInterests().contains(interest);
    }

    private void saveUser(User user) {
        log.info("Saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    private void addRoleToUser(String email, ERole roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = userRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
}
