package id.careerfund.api.domains.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
public class TokenResponse extends ResponseTemplate {
    private String jwttoken;
    private String tokentype;
    private String refreshtoken;
    private Collection roles;

    public TokenResponse(String statusMessage, String statusCode, String jwtToken, String tokenType, Collection roles, String refreshtoken) {
        super(statusMessage, statusCode);
        this.jwttoken = jwtToken;
        this.tokentype = tokenType;
        this.roles = roles;
        this.refreshtoken = refreshtoken;
    }
}
