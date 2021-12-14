package id.careerfund.api.services;

import id.careerfund.api.domains.entities.FinancialTransaction;
import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.entities.User;

public interface BalanceService {
    void addBalanceToUser(User user);

    void setLenderPayback(Funding funding, Loan loan, FinancialTransaction financialTransaction);
}
