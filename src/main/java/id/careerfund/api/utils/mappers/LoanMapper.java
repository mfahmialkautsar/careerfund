package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.models.responses.LoanBorrowerDto;
import id.careerfund.api.domains.models.responses.LoanDto;
import id.careerfund.api.repositories.LoanRepository;
import id.careerfund.api.services.FundingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class LoanMapper {
    private final LoanRepository loanRepo;
    private final ModelMapper modelMapper;
    @Lazy
    private final FundingService fundingService;

    public LoanBorrowerDto entityToBorrowerDto(Loan loan) {
        LoanBorrowerDto loanDto = modelMapper.map(loan, LoanBorrowerDto.class);
        loanDto.setTargetFund(loanDto.getTotalPayment());
        loanDto.setMonthsPaid(loan.getLoanPayments().size() - 1);
        loanDto.setProgress(getLoanProgress(loan));
        loanDto.setFundable(isFundable(loan));
        loanDto.setFundLeft(loan.getTotalPayment() - fundingService.getTotalLoanFund(loan));
        return loanDto;
    }

    private boolean isFundable(Loan loan) {
        return isAmountFundable(loan) && loan.getLoanPayments().size() <= 1;
    }

    private boolean isAmountFundable(Loan loan) {
        Long totalFund = fundingService.getTotalLoanFund(loan);
        return totalFund < loan.getTotalPayment();
    }

    private double getLoanProgress(Loan loan) {
        long funded = 0;
        for (int i = 0; i < loan.getLoanPayments().size(); i++) {
            if (i == 0)
                continue;
            funded += loan.getLoanPayments().get(i).getPayment().getFinancialTransaction().getNominal();
        }
        return (double) funded / (double) loan.getTotalPayment();
    }

    public LoanDto entityToDto(Loan loan, Long userId) {
        LoanDto loanDto = modelMapper.map(loan, LoanDto.class);
        loanDto.setTargetFund(loanDto.getTotalPayment());
        loanDto.setMonthsPaid(Math.max(loan.getLoanPayments().size() - 1, 0));
        loanDto.setProgress(getLoanProgress(loan));
        loanDto.setFundable(isFundable(loan));
        loanDto.setFundedByMe(loanRepo.existsByIdAndFundings_Lender_Id(loan.getId(), userId));
        loanDto.setFundLeft(loan.getTotalPayment() - fundingService.getTotalLoanFund(loan));
        return loanDto;
    }
}
