package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FundingServiceImpl implements FundingService {
    @Override
    public Long getTotalLoanFund(Loan loan) {
        long totalFund = 0L;
        for (Funding funding : loan.getFundings()) {
            totalFund += funding.getFinancialTransaction().getNominal().longValue();
        }
        return totalFund;
    }

    @Override
    public Double getMonthlyCapital(Funding funding) {
        return funding.getFinancialTransaction().getNominal() / (double) funding.getLoan().getTenorMonth();
    }
}
