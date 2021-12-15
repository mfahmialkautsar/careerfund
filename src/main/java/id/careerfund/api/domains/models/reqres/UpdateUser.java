package id.careerfund.api.domains.models.reqres;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUser {
    @NotEmpty(message = "Name is mandatory")
    private String name;
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Please input email")
    private String email;
    private String phoneNumber;
    private String address;
}
