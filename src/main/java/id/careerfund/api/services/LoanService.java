package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.models.requests.FundLoan;
import id.careerfund.api.domains.models.responses.LoanResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.web.firewall.RequestRejectedException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

public interface LoanService {
    Double getInterestPercent(Class aClass, Integer tenorMonth);

    Long getInterestNumber(Class aClass, Integer tenorMonth, Long downPayment);

    Long getMonthlyAdminFee(Class aClass, Integer tenorMonth, Long downPayment);

    Long getAdminFee(Class aClass, Integer tenorMonth, Long downPayment);

    Long getMonthlyPaymentWithoutAdminFee(Class aClass, Integer tenorMonth, Long downPayment);

    Long getMonthlyPayment(Class aClass, Integer tenorMonth, Long downPayment);

    Long getTotalPaymentWithoutAdminFeeAndInterest(Class aClass, Long downPayment);

    Long getTotalPaymentWithoutAdminFee(Class aClass, Integer tenorMonth, Long downPayment);

    Long getTotalPayment(Class aClass, Integer tenorMonth, Long downPayment);

    Double getLenderPayback(Funding funding);

    Page<LoanResponse> getLoans(Principal principal, String sort, String order);

    Page<LoanResponse> getMyLoans(Principal principal, String sort, String order);

    LoanResponse fundLoan(Principal principal, FundLoan fundLoan) throws RequestRejectedException, EntityNotFoundException;
}
