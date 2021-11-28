package id.careerfund.api.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.*;
import javassist.NotFoundException;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

public interface UserService {
    void registerUser(UserRegister userRegister) throws Exception;

    void registerUserIfNotExists(UserRegister userRegister);

    void registerAdminIfNotExists(User userRegister);

    Role saveRole(Role role);

    Role saveRoleIfNotExists(Role role);

    User getUser(String email);

    List<User> getUsers();

    boolean getIsEmailAvailable(String email);

    Interest addInterest(User user, Long id);

    Interest deleteInterest(User user, Long id);

    void addInterests(Principal principal, UpdateInterest updateInterest);

    void deleteInterests(Principal principal, UpdateInterest updateInterest);

    MyInterests getMyInterests(Principal principal);

    UpdateInterest saveInterests(Principal principal, UpdateInterest updateInterest);

    Boolean isUserHasInterest(User user, Interest interest);

    void sendVerificationEmail(EmailRequest emailRequest) throws MessagingException, NotFoundException;

    TokenResponse verifyUser(OtpRequest otpRequest) throws NotFoundException, TokenExpiredException;
}
