package id.careerfund.api.services;

import java.util.List;

public interface TokenService {
    Boolean verifyUser(String email);

    Boolean authUser(String email, String password);

    List<String> roles(String email) throws Exception  ;

    String refreshToken(String email);
}
