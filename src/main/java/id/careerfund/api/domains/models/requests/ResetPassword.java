package id.careerfund.api.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    @NotEmpty(message = "Cannot null")
    private String token;
    @NotEmpty(message = "Cannot null")
    private String newPassword;
}
