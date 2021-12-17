package id.careerfund.api.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.reqres.AssessmentScore;
import id.careerfund.api.domains.models.reqres.UpdateUser;
import id.careerfund.api.domains.models.requests.EmailRequest;
import id.careerfund.api.domains.models.requests.IdRequest;
import id.careerfund.api.domains.models.requests.OtpRequest;
import id.careerfund.api.domains.models.requests.SignInRequest;
import id.careerfund.api.domains.models.requests.UpdateInterest;
import id.careerfund.api.domains.models.requests.UserRegister;
import id.careerfund.api.domains.models.responses.FileUrlResponse;
import id.careerfund.api.domains.models.responses.MyInterests;
import id.careerfund.api.domains.models.responses.MyProfile;
import id.careerfund.api.domains.models.responses.TokenResponse;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

public interface UserService {
    void registerUser(UserRegister userRegister) throws Exception;

    User getUser(String email);

    // List<User> getUsers();

    boolean getIsEmailAvailable(String email);

    // Interest addInterest(User user, Long id);
    //
    // Interest deleteInterest(User user, Long id);

    // void addInterests(Principal principal, UpdateInterest updateInterest);
    //
    // void deleteInterests(Principal principal, UpdateInterest updateInterest);

    MyInterests getMyInterests(Principal principal);

    UpdateInterest saveInterests(Principal principal, UpdateInterest updateInterest);

    Boolean isUserHasInterest(User user, Interest interest);

    void sendVerificationEmail(EmailRequest emailRequest) throws MessagingException, NotFoundException;

    TokenResponse signIn(SignInRequest signInRequest) throws Exception;

    TokenResponse verifyUser(OtpRequest otpRequest) throws NotFoundException, TokenExpiredException;

    UpdateUser getProfileUpdate(Principal principal);

    UpdateUser updateUser(Principal principal, UpdateUser updateUser);

    MyProfile getMyProfile(Principal principal);

    FileUrlResponse uploadPhoto(Principal principal, MultipartFile file);

    FileUrlResponse uploadIdentityCard(Principal principal, MultipartFile file);

    FileUrlResponse uploadSelfie(Principal principal, MultipartFile file);

    AssessmentScore saveAssessmentScore(Principal principal, AssessmentScore assessmentScore);

    AssessmentScore getAssessmentScore(Principal principal);

    void verifyUserId(IdRequest idRequest);

    List<User> getWaitingForVerificationUser();

    void requestVerify(Principal principal);
}
