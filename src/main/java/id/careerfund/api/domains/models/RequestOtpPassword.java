package id.careerfund.api.domains.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestOtpPassword {
    @NotEmpty(message = "Name is mandatory")
    public String email;
}
