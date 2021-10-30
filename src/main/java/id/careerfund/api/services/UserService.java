package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.UserRegister;

import java.util.List;

public interface UserService {
    void registerUser(UserRegister userRegister) throws Exception;

    Role saveRole(Role role);

    User getUser(String email);

    List<User> getUsers();

    boolean getIsEmailAvailable(String email);
}
