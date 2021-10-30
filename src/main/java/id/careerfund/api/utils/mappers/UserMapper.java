package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.UserRegister;

public final class UserMapper {
    public static User userRegisterToUser(UserRegister userRegister) {
        User user = new User();
        user.setName(userRegister.getName());
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        return user;
    }
}
