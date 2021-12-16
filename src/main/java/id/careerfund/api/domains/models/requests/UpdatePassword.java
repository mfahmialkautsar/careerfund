package id.careerfund.api.domains.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdatePassword {
    @NotEmpty(message = "NewPassword is mandatory")
    public String newPassword;
}
