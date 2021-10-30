package id.careerfund.api.domains.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailAvailability {
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Please input email")
    private String email;
}
