package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.TokenResponse;
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
}
