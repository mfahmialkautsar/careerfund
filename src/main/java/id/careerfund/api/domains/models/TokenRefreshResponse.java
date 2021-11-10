package id.careerfund.api.domains.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshResponse {
    private String jwttoken;
    private String tokentype = "Bearer ";
    private String refreshtoken;

    public TokenRefreshResponse(String jwttoken, String refreshtoken) {
        this.jwttoken = jwttoken;
        this.refreshtoken = refreshtoken;
    }
}