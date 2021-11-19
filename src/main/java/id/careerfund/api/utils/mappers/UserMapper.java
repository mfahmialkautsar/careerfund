package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.SimpleUser;
import id.careerfund.api.domains.models.UserRegister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public final class UserMapper {
    public static User userRegisterToUser(UserRegister userRegister) {
        User user = new User();
        user.setName(userRegister.getName());
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        return user;
    }

    public static User principalToUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }

    public static SimpleUser principalToSimpleUser(Principal principal) {
        User user = principalToUser(principal);
        return new SimpleUser(user.getName(), user.getEmail());
    }
}
