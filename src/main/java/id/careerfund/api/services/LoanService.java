package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.models.requests.FundLoan;
import id.careerfund.api.domains.models.responses.FundingDto;
import id.careerfund.api.domains.models.responses.LoanDto;
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

    Page<LoanDto> getLoans(Principal principal, String sort, String order);

    LoanDto getLoanById(Principal principal, Long id);

    FundingDto fundLoan(Principal principal, Long loanId, FundLoan fundLoan)
            throws RequestRejectedException, EntityNotFoundException;
}
