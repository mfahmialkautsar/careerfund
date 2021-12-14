package id.careerfund.api.services;

import id.careerfund.api.domains.EBalanceChange;
import id.careerfund.api.domains.entities.*;
import id.careerfund.api.repositories.BalanceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService {
    private final BalanceHistoryRepository balanceHistoryRepo;
    private final LoanService loanService;

    @Override
    public void addBalanceToUser(User user) {
        user.setBalance(new Balance());
    }

    @Override
    public void setLenderPayback(Funding funding, Loan loan, FinancialTransaction financialTransaction) {
        Double lenderBalance = funding.getLender().getBalance().getNominal();
        Double lenderPayback = loanService.getLenderPayback(loan, funding);
        funding.getLender()
                .getBalance()
                .setNominal(lenderBalance + lenderPayback);
        BalanceHistory balanceHistory = new BalanceHistory();
        balanceHistory.setBalance(funding.getLender().getBalance());
        balanceHistory.setNominal(lenderPayback);
        balanceHistory.setFinancialTransaction(financialTransaction);
        balanceHistory.setBalanceChangeType(EBalanceChange.PAYBACK);
        balanceHistoryRepo.save(balanceHistory);
    }
}
