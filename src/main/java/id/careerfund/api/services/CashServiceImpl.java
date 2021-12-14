package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Cash;
import id.careerfund.api.domains.entities.FinancialTransaction;
import id.careerfund.api.repositories.CashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepo;

    @Override
    public void doDebit(FinancialTransaction financialTransaction) {
        Cash cash = new Cash();
        cash.setFinancialTransaction(financialTransaction);
        cash.setChange((double) financialTransaction.getNominal());
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
        cash.setChange((double) -financialTransaction.getNominal());
        Cash lastCash = cashRepo.findFirstByOrderByCreatedAtDesc();
        Double lastCashBalance = 0.0;
        if (lastCash != null) {
            lastCashBalance = lastCash.getCurrent();
        }
        cash.setCurrent(lastCashBalance - financialTransaction.getNominal());
        cashRepo.save(cash);
    }
}
