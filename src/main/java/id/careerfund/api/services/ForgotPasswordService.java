package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.RequestOtpPassword;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.domains.models.UpdatePassword;

public interface ForgotPasswordService {
    Boolean setUserPassword (String token, String password);

    Boolean checkTokenExpire (User user);

    Boolean checkUser (String email);

    Boolean checkOtp(String email) ;

    String saveOtp (String email, int expiredToken) ;

    String randomString(int size, boolean numberOnly);

    ResponseTemplate sendEmailForgot(RequestOtpPassword model) throws Exception;

    void sendEmail(String email, User user) throws Exception;

    ResponseTemplate resetPassword(String token, UpdatePassword model) throws Exception;
}
