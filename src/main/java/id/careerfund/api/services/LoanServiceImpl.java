package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.repositories.LoanRepository;
import id.careerfund.api.utils.helpers.PageableHelper;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepo;

    @Override
    public Double getInterestPercent(Class aClass, Integer tenorMonth) {
        return 5.0 * (tenorMonth/12.0);
    }

    @Override
    public Long getInterestNumber(Class aClass, Integer tenorMonth, Long downPayment) {
        return (long) Math.ceil((getInterestPercent(aClass, tenorMonth)/100) * getTotalPaymentWithoutAdminFeeAndInterest(aClass, downPayment));
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

    public Double getLenderPayback(Loan loan, Funding funding) {
        return (double) (loan.getInterestNumber() * funding.getFinancialTransaction().getNominal()) + funding.getFinancialTransaction().getNominal();
    }

    @Override
    public Page<Loan> getLoans(String sort, String order) {
        Pageable pageable = PageableHelper.getPageable(sort, order);
        return loanRepo.findDistinctByLoanPaymentsNotEmpty(pageable);
    }

    @Override
    public Page<Loan> getMyLoans(Principal principal, String sort, String order) {
        User user = UserMapper.principalToUser(principal);
        Pageable pageable = PageableHelper.getPageable(sort, order);
        return loanRepo.findDistinctByFundings_IdIs(user.getId(), pageable);
    }
}
