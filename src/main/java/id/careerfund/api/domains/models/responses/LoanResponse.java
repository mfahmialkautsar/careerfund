package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.LoanPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanResponse {
    private Long id;
    private Borrower borrower;
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
    private Integer monthPaid;
    private Long totalPayment;
    private Long monthlyPayment;
    private List<LoanPayment> loanPayments = new ArrayList<>();
}