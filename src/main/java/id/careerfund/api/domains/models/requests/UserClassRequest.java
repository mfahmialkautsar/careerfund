package id.careerfund.api.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserClassRequest {
    @NotNull
    @Positive
    private Double downPayment;
    @NotNull
    @Positive
    private Integer tenorMonth;
    @NotNull
    @Positive
    private Long classId;
}
