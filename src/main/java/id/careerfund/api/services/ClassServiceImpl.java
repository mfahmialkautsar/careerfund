package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.repositories.ClassRepository;
import id.careerfund.api.repositories.UserClassRepository;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepo;
    private final UserClassRepository userClassRepo;

    @Override
    public Page<Class> getClasses(Collection<String> categories, Collection<String> institutions, String name, Double feeStart, Double feeEnd, String sort, String order) {
        Sort sortOrder = null;
        if (sort != null) {
            if (Objects.equals(order.toLowerCase(), "desc")) {
                sortOrder = Sort.by(sort).descending();
            } else if (Objects.equals(order.toLowerCase(), "asc")) {
                sortOrder = Sort.by(sort).ascending();
            }
        }

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        if (sortOrder != null) {
            pageable = PageRequest.of(0, Integer.MAX_VALUE, sortOrder);
        }
        return classRepo.findByBootcamp_Categories_NameInAndBootcamp_Institutions_NameInAndBootcamp_NameIsLikeIgnoreCaseAndFeeGreaterThanEqualAndFeeLessThanEqual(categories, institutions, name, feeStart, feeEnd, pageable);
    }

    @Override
    public List<UserClass> getMyClasses(Principal principal) {
        return userClassRepo.findByUser(UserMapper.principalToUser(principal));
    }
}
