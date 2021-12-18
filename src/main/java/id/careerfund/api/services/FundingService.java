package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.models.requests.WithdrawRequest;
import javassist.NotFoundException;
import org.springframework.security.web.firewall.RequestRejectedException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

public interface FundingService {
    Long getTotalLoanFund(Loan loan);

    Double getMonthlyCapital(Funding funding);

    void withdrawLoan(Principal principal, Long fundingId, WithdrawRequest withdrawRequest) throws EntityNotFoundException, RequestRejectedException;
}
