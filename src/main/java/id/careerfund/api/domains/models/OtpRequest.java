package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    @NotEmpty(message = "Cannot null")
    private String code;
}
