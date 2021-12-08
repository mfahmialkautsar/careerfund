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

    @Override
    public UserClass registerClass(Principal principal, UserClassRequest userClassRequest) throws EntityNotFoundException {
        log.info("Registering class {}", userClassRequest.getClassId());
        User user = UserMapper.principalToUser(principal);
        Class aClass = classRepo.getById(userClassRequest.getClassId());
        Loan loan = new Loan();
        loan.setBorrower(user);
        loan.setDownPayment(userClassRequest.getDownPayment());
        loan.setInterestPercent(aClass.getInterestPercent());
        loan.setTenorMonth(userClassRequest.getTenorMonth());
        Double interestNumber = aClass.getPrice() * userClassRequest.getTenorMonth() * aClass.getInterestPercent();
        loan.setInterestNumber(interestNumber);
        loan.setTotalPayment(aClass.getPrice() + interestNumber);
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
