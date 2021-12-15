package id.careerfund.api.domains.models.responses;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.FinancialTransaction;
import id.careerfund.api.domains.entities.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserClassResponse {
    private Long id;
    private Class aClass;
    private Loan loan;
    private Double score;
    private FinancialTransaction transferredToBootcamp;
    private Boolean isDpPaid;
}
