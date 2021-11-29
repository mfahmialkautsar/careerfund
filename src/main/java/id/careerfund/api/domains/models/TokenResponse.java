package id.careerfund.api.domains.models;

import id.careerfund.api.domains.ERole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private String tokenType = "Bearer ";
    private final String refreshToken;
    private final Collection<ERole> roles;
}
