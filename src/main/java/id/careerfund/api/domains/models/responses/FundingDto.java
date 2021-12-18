package id.careerfund.api.domains.models.responses;

import id.careerfund.api.domains.entities.FinancialTransaction;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.entities.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundingDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long id;
    private LoanResponse loan;
    private FinancialTransaction financialTransaction;
    private Withdraw withdraw;
    private Boolean withdrawn;
}
