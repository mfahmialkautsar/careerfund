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
    public Double getPaymentWithoutAdminFee(Class aClass, Double downPayment) {
        return aClass.getPrice() - downPayment;
    }

    @Override
    public Double getInterestPercent(Class aClass, Integer tenorMonth) {
        return 5.0 * (tenorMonth/12.0);
    }

    @Override
    public Double getInterestNumber(Class aClass, Integer tenorMonth, Double downPayment) {
        return getInterestPercent(aClass, tenorMonth) * getPaymentWithoutAdminFee(aClass, downPayment);
    }

    @Override
    public Double getMonthlyAdminFee(Class aClass, Integer tenorMonth, Double downPayment) {
        return getMonthlyPaymentWithoutAdminFee(aClass, tenorMonth, downPayment) * (0.02);
    }

    @Override
    public Double getAdminFee(Class aClass, Integer tenorMonth, Double downPayment) {
        return getMonthlyAdminFee(aClass, tenorMonth, downPayment) * tenorMonth;
    }

    @Override
    public Double getMonthlyPayment(Class aClass, Integer tenorMonth, Double downPayment) {
        return getMonthlyPaymentWithoutAdminFee(aClass, tenorMonth, downPayment) + getMonthlyAdminFee(aClass, tenorMonth, downPayment);
    }

    @Override
    public Double getMonthlyPaymentWithoutAdminFee(Class aClass, Integer tenorMonth, Double downPayment) {
        return getPaymentWithoutAdminFee(aClass, downPayment) / tenorMonth;
    }

    @Override
    public Double getTotalPayment(Class aClass, Integer tenorMonth, Double downPayment) {
        return getPaymentWithoutAdminFee(aClass, downPayment) + getInterestNumber(aClass, tenorMonth, downPayment);
    }
}
