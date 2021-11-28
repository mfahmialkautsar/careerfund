package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Please input email")
    private String email;
}
