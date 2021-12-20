package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Withdrawals;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface WithdrawalService {
    Page<Withdrawals> getWithdrawals(Principal principal, String sort, String order);
}
