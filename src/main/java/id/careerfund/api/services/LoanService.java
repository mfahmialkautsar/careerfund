package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;

public interface LoanService {
    Double getPaymentWithoutAdminFee(Class aClass, Double downPayment);

    Double getInterestPercent(Class aClass, Integer tenorMonth);

    Double getInterestNumber(Class aClass, Integer tenorMonth, Double downPayment);

    Double getMonthlyAdminFee(Class aClass, Integer tenorMonth, Double downPayment);

    Double getAdminFee(Class aClass, Integer tenorMonth, Double downPayment);

    Double getMonthlyPayment(Class aClass, Integer tenorMonth, Double downPayment);

    Double getMonthlyPaymentWithoutAdminFee(Class aClass, Integer tenorMonth, Double downPayment);

    Double getTotalPayment(Class aClass, Integer tenorMonth, Double downPayment);
}
