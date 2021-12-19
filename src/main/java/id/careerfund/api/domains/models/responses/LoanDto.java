package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.LoanPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanDto {
    private Long id;
    private Borrower borrower;
    private Double interestPercent;
    private Integer tenorMonth;
    private Long totalPayment;
    private Long targetFund;
    private Class aClass;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean fundable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double progress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean fundedByMe;
    private Long fundLeft;
    private Integer monthsPaid;
    private Long monthlyPayment;
    private List<LoanPayment> loanPayments = new ArrayList<>();
}
