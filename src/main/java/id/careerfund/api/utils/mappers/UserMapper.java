package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.SimpleUser;
import id.careerfund.api.domains.models.UserRegister;
import id.careerfund.api.domains.models.reqres.UpdateUser;
import id.careerfund.api.domains.models.responses.MyProfile;
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

    public static UpdateUser principalToUpdateUser(Principal principal) {
        User user = principalToUser(principal);
        UpdateUser updateUser = new UpdateUser();
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setAddress(user.getAddress());
        return updateUser;
    }

    public static UpdateUser userToUpdateUser(User user) {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setAddress(user.getAddress());
        return updateUser;
    }

    public static MyProfile principalToMyProfile(Principal principal) {
        User user = principalToUser(principal);
        MyProfile myProfile = new MyProfile();
        myProfile.setId(user.getId());
        myProfile.setName(user.getName());
        myProfile.setEmail(user.getEmail());
        myProfile.setRoles(user.getRoles());
        myProfile.setPhoneNumber(user.getPhoneNumber());
        myProfile.setAddress(user.getAddress());
        myProfile.setInterests(user.getInterests());
        myProfile.setBalance(user.getBalance());
        myProfile.setPhotoPath(user.getPhotoPath());
        myProfile.setIdentityCardPath(user.getIdentityCardPath());
        myProfile.setAssessmentScore(user.getAssessmentScore());
        return myProfile;
    }
}
