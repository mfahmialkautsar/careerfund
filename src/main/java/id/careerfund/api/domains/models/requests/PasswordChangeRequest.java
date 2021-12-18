package id.careerfund.api.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    @NotEmpty(message = "Cannot null")
    private String oldPassword;
    @NotEmpty(message = "Cannot null")
    private String newPassword;
}
