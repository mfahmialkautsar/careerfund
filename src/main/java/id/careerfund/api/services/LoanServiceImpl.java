package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
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
}
