package id.careerfund.api.controllers.forgetpassword;

import id.careerfund.api.domains.entities.User;

public interface ForgotPasswordService {
    Boolean setUserPassword (String token, String password);

    Boolean checkTokenExpire (User user);

    Boolean checkUser (String email);

    Boolean checkOtp(String email) ;

    String saveOtp (String email, int expiredToken) ;

    String randomString(int size, boolean numberOnly);
}
