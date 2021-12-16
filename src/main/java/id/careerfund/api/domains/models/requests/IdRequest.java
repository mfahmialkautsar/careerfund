package id.careerfund.api.domains.models.requests;

import id.careerfund.api.domains.EIdVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdRequest {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    private EIdVerificationStatus status;
}
