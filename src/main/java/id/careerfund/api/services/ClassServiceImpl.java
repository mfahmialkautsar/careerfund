package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.repositories.ClassRepository;
import id.careerfund.api.utils.helpers.PageableHelper;
import id.careerfund.api.utils.mappers.UserMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepo;

    @Override
    public Page<Class> getClasses(Principal principal, Collection<Long> categories, Collection<Long> institutions, String name, Double priceStart, Double priceEnd, String sort, String order) {
        Pageable pageable = PageableHelper.getPageable(sort, order);
        Page<Class> classes = classRepo.findDistinctByBootcamp_Categories_IdInAndBootcamp_Institutions_IdInAndBootcamp_NameIsLikeIgnoreCaseOrBootcamp_NameIsLikeIgnoreCaseOrInstitutions_NameIsLikeIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(categories, institutions, name, priceStart, priceEnd, pageable);
        classes.getContent().forEach(this::setTransientValues);
        if (principal != null) {
            User user = UserMapper.principalToUser(principal);
            classes.getContent().forEach(aClass -> aClass.setRegistered(aClass.getUserClass().retainAll(user.getUserClasses())));
        }

        return classes;
    }

    @Override
    public Class getClassById(Principal principal, Long id) throws NotFoundException {
        Optional<Class> aClass = classRepo.findById(id);
        if (!aClass.isPresent()) throw new NotFoundException("Class not found");
        if (principal != null) {
            User user = UserMapper.principalToUser(principal);
            aClass.get().setRegistered(aClass.get().getUserClass().retainAll(user.getUserClasses()));
        }
        setTransientValues(aClass.get());

        return aClass.get();
    }

    @Override
    public Long getMonthDuration(Class aClass) {
        return aClass.getStartDate().until(aClass.getEndDate(), ChronoUnit.MONTHS);
    }

    private void setTransientValues(Class aClass) {
        aClass.setDurationMonth(getMonthDuration(aClass).intValue());
    }
}
