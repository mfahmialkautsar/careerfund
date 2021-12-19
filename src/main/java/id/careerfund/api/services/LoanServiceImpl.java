package id.careerfund.api.services;

import id.careerfund.api.domains.entities.*;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.models.requests.FundLoan;
import id.careerfund.api.domains.models.responses.FundingDto;
import id.careerfund.api.domains.models.responses.LoanDto;
import id.careerfund.api.repositories.*;
import id.careerfund.api.utils.helpers.PageableHelper;
import id.careerfund.api.utils.mappers.LoanMapper;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepo;
    private final FinancialTransactionRepository financialTransactionRepo;
    private final FundingRepository fundingRepo;
    private final FundingService fundingService;
    private final CashService cashService;
    private final ModelMapper modelMapper;
    private final LoanMapper loanMapper;

    @Override
    public Double getInterestPercent(Class aClass, Integer tenorMonth) {
        return 5.0 * (tenorMonth / 12.0);
    }

    @Override
    public Long getInterestNumber(Class aClass, Integer tenorMonth, Long downPayment) {
        return (long) Math.ceil((getInterestPercent(aClass, tenorMonth) / 100)
                * getTotalPaymentWithoutAdminFeeAndInterest(aClass, downPayment));
    }

    @Override
    public Long getMonthlyAdminFee(Class aClass, Integer tenorMonth, Long downPayment) {
        return (long) Math.ceil((double) getMonthlyPaymentWithoutAdminFee(aClass, tenorMonth, downPayment) * 0.02);
    }

    @Override
    public Long getAdminFee(Class aClass, Integer tenorMonth, Long downPayment) {
        return getMonthlyAdminFee(aClass, tenorMonth, downPayment) * tenorMonth;
    }

    @Override
    public Long getMonthlyPaymentWithoutAdminFee(Class aClass, Integer tenorMonth, Long downPayment) {
        return getTotalPaymentWithoutAdminFee(aClass, tenorMonth, downPayment) / tenorMonth;
    }

    @Override
    public Long getMonthlyPayment(Class aClass, Integer tenorMonth, Long downPayment) {
        return getTotalPayment(aClass, tenorMonth, downPayment) / tenorMonth;
    }

    @Override
    public Long getTotalPaymentWithoutAdminFeeAndInterest(Class aClass, Long downPayment) {
        return aClass.getPrice() - downPayment;
    }

    @Override
    public Long getTotalPaymentWithoutAdminFee(Class aClass, Integer tenorMonth, Long downPayment) {
        return getTotalPaymentWithoutAdminFeeAndInterest(aClass, downPayment)
                + getInterestNumber(aClass, tenorMonth, downPayment);
    }

    @Override
    public Long getTotalPayment(Class aClass, Integer tenorMonth, Long downPayment) {
        return getTotalPaymentWithoutAdminFee(aClass, tenorMonth, downPayment)
                + getAdminFee(aClass, tenorMonth, downPayment);
    }

    @Override
    public Double getLenderPayback(Funding funding) {
        return (((funding.getLoan().getInterestPercent() / 100.0) * funding.getFinancialTransaction().getNominal())
                / funding.getLoan().getTenorMonth())
                + fundingService.getMonthlyCapital(funding);
    }

    @Override
    public Page<LoanDto> getLoans(Principal principal, String sort, String order) {
        User user = UserMapper.principalToUser(principal);
        Pageable pageable = PageableHelper.getPageable(sort, order);
        Page<Loan> loanPage = loanRepo.findDistinctByLoanPaymentsNotEmpty(pageable);

        return loanPage.map(loan -> loanMapper.entityToDto(loan, user.getId()));
    }

    @Override
    public Page<LoanDto> getMyLoans(Principal principal, String sort, String order) {
        User user = UserMapper.principalToUser(principal);
        Pageable pageable = PageableHelper.getPageable(sort, order);
        Page<Loan> loanPage = loanRepo.findByFundings_Lender_Id(user.getId(), pageable);

        return loanPage.map(loan -> loanMapper.entityToDto(loan, user.getId()));
    }

    @Override
    public FundingDto fundLoan(Principal principal, FundLoan fundLoan)
            throws RequestRejectedException, EntityNotFoundException {
        User user = UserMapper.principalToUser(principal);

        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setNominal(fundLoan.getFund().doubleValue());
        financialTransactionRepo.save(financialTransaction);

        Optional<Loan> optionalLoan = loanRepo.findById(fundLoan.getLoanId());

        if (!optionalLoan.isPresent())
            throw new EntityNotFoundException("LOAN_NOT_FOUND");
        Loan loan = optionalLoan.get();

        if (!isAmountFundable(loan))
            throw new RequestRejectedException("LOAN_FUNDING_FULL");
        if (fundLoan.getFund() > loan.getTotalPayment()
                || fundLoan.getFund() > loan.getTotalPayment() - fundingService.getTotalLoanFund(loan))
            throw new RequestRejectedException("MAX_EXCEEDED");
        if (loan.getLoanPayments().size() > 1 || loan.getUserClass().getAClass().getStartDate().isBefore(LocalDate.now()))
            throw new RequestRejectedException("LOAN_STARTED");
        if (fundLoan.getFund() < getMinFund(loan))
            throw new RequestRejectedException(String.valueOf(getMinFund(loan)));

        Funding funding = new Funding();
        funding.setLender(user);
        funding.setFinancialTransaction(financialTransaction);
        funding.setLoan(loan);
        fundingRepo.save(funding);

        loan.getFundings().add(funding);

        cashService.doDebit(financialTransaction);

        finishFund(loan);

        return modelMapper.map(funding, FundingDto.class);
    }

    private boolean isAmountFundable(Loan loan) {
        Long totalFund = fundingService.getTotalLoanFund(loan);
        return totalFund < loan.getTotalPayment();
    }

    private Long getMinFund(Loan loan) {
        Long totalFund = fundingService.getTotalLoanFund(loan);
        Long neededFund = loan.getTotalPayment();
        long deviation = neededFund - totalFund;
        if (deviation < 200000)
            return deviation;
        return 100000L;
    }

    private void finishFund(Loan loan) {
        if (!isAmountFundable(loan)) {
            FinancialTransaction financialTransaction = new FinancialTransaction();
            financialTransaction.setNominal(loan.getTotalPayment() + loan.getDownPayment().doubleValue());
            financialTransactionRepo.save(financialTransaction);
            loan.getUserClass().setTransferredToBootcamp(financialTransaction);
            cashService.doCredit(financialTransaction);
        }
    }
}
