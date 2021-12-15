package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.Loan;

public interface FundingService {
    Long getTotalLoanFund(Loan loan);
    Double getMonthlyCapital(Funding funding);
}
