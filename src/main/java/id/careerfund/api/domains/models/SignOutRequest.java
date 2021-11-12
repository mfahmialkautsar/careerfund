package id.careerfund.api.domains.models;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignOutRequest {
    @NotEmpty(message = "Refresh Token is mandatory")
    private String refreshtoken;
}
