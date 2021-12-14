package id.careerfund.api.services;

import id.careerfund.api.domains.entities.FinancialTransaction;

public interface CashService {
    void doDebit(FinancialTransaction financialTransaction);
    void doCredit(FinancialTransaction financialTransaction);
}
