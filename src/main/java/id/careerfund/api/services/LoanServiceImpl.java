package id.careerfund.api.services;

import id.careerfund.api.domains.entities.*;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.models.requests.FundLoan;
import id.careerfund.api.domains.models.responses.LoanResponse;
import id.careerfund.api.repositories.FinancialTransactionRepository;
import id.careerfund.api.repositories.FundingRepository;
import id.careerfund.api.repositories.LoanRepository;
import id.careerfund.api.utils.helpers.PageableHelper;
import id.careerfund.api.utils.mappers.LoanMapper;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepo;
    private final FinancialTransactionRepository financialTransactionRepo;
    private final FundingRepository fundingRepo;
    private final FundingService fundingService;
    private final CashService cashService;

    @Override
    public Double getInterestPercent(Class aClass, Integer tenorMonth) {
        return 5.0 * (tenorMonth / 12.0);
    }

    @Override
    public Long getInterestNumber(Class aClass, Integer tenorMonth, Long downPayment) {
        return (long) Math.ceil((getInterestPercent(aClass, tenorMonth) / 100) * getTotalPaymentWithoutAdminFeeAndInterest(aClass, downPayment));
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
        return getTotalPaymentWithoutAdminFeeAndInterest(aClass, downPayment) + getInterestNumber(aClass, tenorMonth, downPayment);
    }

    @Override
    public Long getTotalPayment(Class aClass, Integer tenorMonth, Long downPayment) {
        return getTotalPaymentWithoutAdminFee(aClass, tenorMonth, downPayment) + getAdminFee(aClass, tenorMonth, downPayment);
    }

    @Override
    public Double getLenderPayback(Funding funding) {
        return (((funding.getLoan().getInterestPercent() / 100.0) * funding.getFinancialTransaction().getNominal()) / funding.getLoan().getTenorMonth())
                + fundingService.getMonthlyCapital(funding);
    }

    @Override
    public Page<LoanResponse> getLoans(Principal principal, String sort, String order) {
        User user = UserMapper.principalToUser(principal);
        Pageable pageable = PageableHelper.getPageable(sort, order);
        Page<Loan> loanPage = loanRepo.findDistinctByLoanPaymentsNotEmpty(pageable);
        Page<LoanResponse> loansResponse = loanPage.map(LoanMapper::entityToResponse);

        setPageTransientValues(loanPage, loansResponse, user.getId());
        return loansResponse;
    }

    @Override
    public Page<LoanResponse> getMyLoans(Principal principal, String sort, String order) {
        User user = UserMapper.principalToUser(principal);
        Pageable pageable = PageableHelper.getPageable(sort, order);
        Page<Loan> loanPage = loanRepo.findByFundings_Lender_Id(user.getId(), pageable);
        Page<LoanResponse> loansResponse = loanPage.map(LoanMapper::entityToResponse);

        setPageTransientValues(loanPage, loansResponse, user.getId());
        return loansResponse;
    }

    @Override
    public LoanResponse fundLoan(Principal principal, FundLoan fundLoan) throws RequestRejectedException, EntityNotFoundException {
        User user = UserMapper.principalToUser(principal);

        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setNominal(fundLoan.getFund().doubleValue());
        financialTransactionRepo.save(financialTransaction);

        Optional<Loan> optionalLoan = loanRepo.findById(fundLoan.getLoanId());

        if (!optionalLoan.isPresent()) throw new EntityNotFoundException("LOAN_NOT_FOUND");
        Loan loan = optionalLoan.get();

        if (!isFundable(loan)) throw new RequestRejectedException("LOAN_FUNDING_FULL");
        if (fundLoan.getFund() > loan.getTotalPayment() || loan.getTotalPayment() > loan.getTotalPayment() - fundingService.getTotalLoanFund(loan)) throw new RequestRejectedException("MAX_EXCEEDED");
        if (fundLoan.getFund() < getMinFund(loan)) throw new RequestRejectedException(String.valueOf(getMinFund(loan)));

        Funding funding = new Funding();
        funding.setLender(user);
        funding.setFinancialTransaction(financialTransaction);
        funding.setLoan(loan);
        fundingRepo.save(funding);

        loan.getFundings().add(funding);

        cashService.doDebit(financialTransaction);

        finishFund(loan);

        LoanResponse loanResponse = LoanMapper.entityToResponse(loan);
        setLoanTransientValues(loan, loanResponse, user.getId());
        return loanResponse;
    }

    private boolean isFundable(Loan loan) {
        Long totalFund = fundingService.getTotalLoanFund(loan);
        return totalFund < loan.getTotalPayment();
    }

    private Long getMinFund(Loan loan) {
        Long totalFund = fundingService.getTotalLoanFund(loan);
        Long neededFund = loan.getTotalPayment();
        long deviation = neededFund - totalFund;
        if (deviation < 200000) return deviation;
        return 100000L;
    }

    private void finishFund(Loan loan) {
        if (!isFundable(loan)) {
            FinancialTransaction financialTransaction = new FinancialTransaction();
            financialTransaction.setNominal(loan.getTotalPayment() + loan.getDownPayment().doubleValue());
            financialTransactionRepo.save(financialTransaction);
            loan.getUserClass().setTransferedToBootcamp(financialTransaction);
            cashService.doCredit(financialTransaction);
        }
    }

    private double getLoanProgress(Loan loan) {
        long funded = 0;
        for (int i = 0; i < loan.getLoanPayments().size(); i++) {
            if (i == 0) continue;
            funded += loan.getLoanPayments().get(i).getPayment().getFinancialTransaction().getNominal();
        }
        return (double) funded / (double) loan.getTotalPayment();
    }

    private void setPageTransientValues(Page<Loan> loanPage, Page<LoanResponse> loanResponse, long userId) {
        for (int i = 0; i < loanPage.getContent().size(); i++) {
            setLoanTransientValues(loanPage.getContent().get(i), loanResponse.getContent().get(i), userId);
        }
    }

    private void setLoanTransientValues(Loan loan, LoanResponse loanResponse, long userId) {
        loanResponse.setProgress(getLoanProgress(loan));
        loanResponse.setMonthPaid(loan.getLoanPayments().size() - 1);
        loanResponse.setFundable(isFundable(loan));
        loanResponse.setFundedByMe(loanRepo.existsByFundings_Lender_Id(userId));
        loanResponse.setFundLeft(loan.getTotalPayment() - fundingService.getTotalLoanFund(loan));
    }
}
