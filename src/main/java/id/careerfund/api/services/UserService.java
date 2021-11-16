package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.MyInterestResponse;
import id.careerfund.api.domains.models.TokenResponse;
import id.careerfund.api.domains.models.UpdateInterest;
import id.careerfund.api.domains.models.UserRegister;

import java.util.List;

public interface UserService {
    TokenResponse registerUser(UserRegister userRegister) throws Exception;

    void registerUserIfNotExists(UserRegister userRegister);

    void registerAdminIfNotExists(User userRegister);

    Role saveRole(Role role);

    Role saveRoleIfNotExists(Role role);

    User getUser(String email);

    List<User> getUsers();

    boolean getIsEmailAvailable(String email);

    Interest saveInterest(User user, Long id);

    Interest deleteInterest(User user, Long id);

    void saveInterests(String email, List<Long> id);

    void deleteInterests(String email, List<Long> id);

    MyInterestResponse fetchInterests(String email);

    UpdateInterest updateInterest(String email, UpdateInterest updateInterest);

    Boolean isUserHasInterest(User user, Interest interest);
}
