package id.careerfund.api.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FundLoan {
    @NotNull
    @Positive
    private Long loanId;
    @NotNull
    @Positive
    @Min(100000)
    private Long fund;
}
