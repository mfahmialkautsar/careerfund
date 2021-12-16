package id.careerfund.api.services;

import id.careerfund.api.domains.entities.FinancialTransaction;

public interface CashService {
    void doDebit(FinancialTransaction financialTransaction);

    void doCredit(FinancialTransaction financialTransaction);

    void takeMonthlyFee(FinancialTransaction financialTransaction, Double fee);
}
