package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.requests.UserClassRequest;
import id.careerfund.api.repositories.ClassRepository;
import id.careerfund.api.repositories.LoanRepository;
import id.careerfund.api.repositories.UserClassRepository;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserClassServiceImpl implements UserClassService {
    private final LoanRepository loanRepo;
    private final UserClassRepository userClassRepo;
    private final ClassRepository classRepo;
    private final LoanService loanService;

    @Override
    public UserClass registerClass(Principal principal, UserClassRequest userClassRequest) throws EntityNotFoundException {
        log.info("Registering class {}", userClassRequest.getClassId());
        User user = UserMapper.principalToUser(principal);
        Class aClass = classRepo.getById(userClassRequest.getClassId());
        if (userClassRequest.getDownPayment() > aClass.getPrice() * 0.3) throw new RequestRejectedException("DOWNPAYMENT_GREATER");
        if (userClassRequest.getDownPayment() < aClass.getPrice() * 0.1) throw new RequestRejectedException("DOWNPAYMENT_LESS");
        Loan loan = new Loan();
        loan.setBorrower(user);
        loan.setDownPayment(userClassRequest.getDownPayment());
        loan.setTenorMonth(userClassRequest.getTenorMonth());
        loan.setInterestPercent(loanService.getInterestPercent(aClass, userClassRequest.getTenorMonth()));
        loan.setInterestNumber(loanService.getInterestNumber(aClass, userClassRequest.getTenorMonth(), userClassRequest.getDownPayment()));
        loan.setMonthlyFee(loanService.getMonthlyAdminFee(aClass, userClassRequest.getTenorMonth(), userClassRequest.getDownPayment()));
        loan.setFee(loanService.getAdminFee(aClass, userClassRequest.getTenorMonth(), userClassRequest.getDownPayment()));
        loan.setMonthlyPayment(loanService.getMonthlyPayment(aClass, userClassRequest.getTenorMonth(), userClassRequest.getDownPayment()));
        loan.setTotalPayment(loanService.getTotalPayment(aClass, userClassRequest.getTenorMonth(), userClassRequest.getDownPayment()));
        loanRepo.save(loan);
        UserClass userClass = new UserClass();
        userClass.setAClass(aClass);
        userClass.setUser(user);
        userClass.setLoan(loan);
        userClassRepo.save(userClass);
        return userClass;
    }

    @Override
    public List<UserClass> getMyClasses(Principal principal) {
        return userClassRepo.findByUser(UserMapper.principalToUser(principal));
    }

    @Override
    public UserClass getMyClassById(Principal principal, Long id) throws AccessDeniedException {
        UserClass userClass = userClassRepo.getById(id);
        User user = UserMapper.principalToUser(principal);
        if (!userClass.getUser().getId().equals(user.getId())) throw new AccessDeniedException("USER_WRONG");
        return userClass;
    }
}