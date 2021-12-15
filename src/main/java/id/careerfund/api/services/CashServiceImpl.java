package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Cash;
import id.careerfund.api.domains.entities.FinancialTransaction;
import id.careerfund.api.repositories.CashRepository;
import id.careerfund.api.repositories.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepo;
    private final FinancialTransactionRepository financialTransactionRepo;

    @Override
    public void doDebit(FinancialTransaction financialTransaction) {
        Cash cash = new Cash();
        cash.setFinancialTransaction(financialTransaction);
        cash.setChange(financialTransaction.getNominal());

        Cash lastCash = cashRepo.findFirstByOrderByCreatedAtDesc();
        Double lastCashBalance = 0.0;
        if (lastCash != null) {
            lastCashBalance = lastCash.getCurrent();
        }
        cash.setCurrent(lastCashBalance + financialTransaction.getNominal());
        cashRepo.save(cash);
    }

    @Override
    public void doCredit(FinancialTransaction financialTransaction) {
        Cash cash = new Cash();
        cash.setFinancialTransaction(financialTransaction);
        cash.setChange(-financialTransaction.getNominal());

        Cash lastCash = cashRepo.findFirstByOrderByCreatedAtDesc();
        Double lastCashBalance = 0.0;
        if (lastCash != null) {
            lastCashBalance = lastCash.getCurrent();
        }
        cash.setCurrent(lastCashBalance - financialTransaction.getNominal());
        cashRepo.save(cash);
    }

    public void takeMonthlyFee(FinancialTransaction financialTransaction, Double fee) {
        FinancialTransaction feeFinancialTransaction = new FinancialTransaction();
        feeFinancialTransaction.setFinancialTransaction(financialTransaction);
        feeFinancialTransaction.setNominal(fee);
        financialTransactionRepo.save(feeFinancialTransaction);

        doDebit(feeFinancialTransaction);
    }
}
