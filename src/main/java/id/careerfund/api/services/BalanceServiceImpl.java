package id.careerfund.api.services;

import id.careerfund.api.domains.EBalanceChange;
import id.careerfund.api.domains.entities.*;
import id.careerfund.api.repositories.BalanceHistoryRepository;
import id.careerfund.api.repositories.BalanceRepository;
import id.careerfund.api.repositories.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService {
    private final BalanceRepository balanceRepo;
    private final BalanceHistoryRepository balanceHistoryRepo;
    private final LoanService loanService;
    private final FinancialTransactionRepository financialTransactionRepo;

    @Override
    public void addBalanceToUser(User user) {
        Balance balance = new Balance();
        balanceRepo.save(balance);
        user.setBalance(balance);
    }

    @Override
    public void sendLenderPayback(Funding funding, FinancialTransaction financialTransaction) {
        if (funding.getLender().getBalance() == null) addBalanceToUser(funding.getLender());
        Double lenderBalance = funding.getLender().getBalance().getNominal();
        Double lenderPayback = loanService.getLenderPayback(funding);
        funding.getLender()
                .getBalance()
                .setNominal(lenderBalance + lenderPayback);

        FinancialTransaction paybackFinancialTransaction = new FinancialTransaction();
        paybackFinancialTransaction.setFinancialTransaction(financialTransaction);
        paybackFinancialTransaction.setNominal(lenderPayback);
        financialTransactionRepo.save(paybackFinancialTransaction);

        BalanceHistory balanceHistory = new BalanceHistory();
        balanceHistory.setBalance(funding.getLender().getBalance());
        balanceHistory.setNominal(lenderPayback);
        balanceHistory.setFinancialTransaction(paybackFinancialTransaction);
        balanceHistory.setBalanceChangeType(EBalanceChange.PAYBACK);
        balanceHistoryRepo.save(balanceHistory);
    }
}
