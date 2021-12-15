package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.LoanPayment;
import id.careerfund.api.domains.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanResponse {
    private Long id;
    private User borrower;
    private Double interestPercent;
    private Integer tenorMonth;
    private Long targetFund;
    private Class aClass;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean fundable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double progress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean fundedByMe;
    private Long fundLeft;
}
