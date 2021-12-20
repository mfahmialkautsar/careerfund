package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.entities.Withdrawals;
import id.careerfund.api.repositories.WithdrawalsRepository;
import id.careerfund.api.utils.helpers.PageableHelper;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {
    private final WithdrawalsRepository withdrawalsRepo;

    @Override
    public Page<Withdrawals> getWithdrawals(Principal principal, String sort, String order) {
        User user = UserMapper.principalToUser(principal);
        Pageable pageable = PageableHelper.getPageable(sort, order);
        return withdrawalsRepo.findDistinctByFunding_Lender_IdAndCreatedAtIn(user.getId(), null, pageable);
    }
}
