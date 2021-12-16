package id.careerfund.api.domains.models.responses;

import id.careerfund.api.domains.ERole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String tokenType = "Bearer";
    private final String refreshToken;
    private final Collection<ERole> roles;
}
