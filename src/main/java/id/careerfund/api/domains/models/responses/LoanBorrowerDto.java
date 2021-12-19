package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.LoanPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanBorrowerDto {
    private Long id;
    private Double interestPercent;
    private Long interestNumber;
    private Integer tenorMonth;
    private Long downPayment;
    private LocalDateTime dpPaymentExpiredTime;
    private Long totalPayment;
    private Long monthlyPayment;
    private Integer monthlyPaymentDueDate;
    private Long monthlyFee;
    private Long fee;
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
    private List<LoanPayment> loanPayments = new ArrayList<>();
}
