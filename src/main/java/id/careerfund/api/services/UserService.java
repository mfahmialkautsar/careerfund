package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.UserRegister;

import java.util.List;

public interface UserService {
    void registerLender(UserRegister userRegister) throws Exception;

    void registerBorrower(UserRegister userRegister) throws Exception;

    void registerLenderIfNotExists(UserRegister userRegister);

    void registerBorrowerIfNotExists(UserRegister userRegister);

    void registerAdminIfNotExist(UserRegister userRegister);

    Role saveRole(Role role);

    Role saveRoleIfNotExists(Role role);

    User getUser(String email);

    List<User> getUsers();

    boolean getIsEmailAvailable(String email);
}
