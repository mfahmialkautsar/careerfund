package id.careerfund.api.domains.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NewTokenRequest {
    @NotEmpty(message = "Refresh Token is mandatory")
    private String refreshToken;
}
