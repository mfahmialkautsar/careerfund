package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Funding;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.models.requests.WithdrawRequest;
import id.careerfund.api.domains.models.responses.FundingDto;
import org.springframework.data.domain.Page;
import org.springframework.security.web.firewall.RequestRejectedException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

public interface FundingService {
    Page<FundingDto> getMyFundings(Principal principal, String sort, String order);

    FundingDto getMyFundingById(Principal principal, Long id) throws EntityNotFoundException;

    Long getTotalLoanFund(Loan loan);

    Double getMonthlyCapital(Funding funding);

    void withdrawLoan(Principal principal, Long fundingId, WithdrawRequest withdrawRequest) throws EntityNotFoundException, RequestRejectedException;
}
