package id.careerfund.api.domains.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TokenResponse {

    private String jwttoken;
    private String tokentype;
    private String refreshtoken;
    private List<String> roles;

    public TokenResponse(String jwtToken, List<String> roles, String refreshtoken) {
        this.jwttoken = jwtToken;
        this.tokentype = "Bearer ";
        this.roles = roles;
        this.refreshtoken = refreshtoken;
    }
}
