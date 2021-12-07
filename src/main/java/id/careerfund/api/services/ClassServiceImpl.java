package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.repositories.ClassRepository;
import id.careerfund.api.repositories.UserClassRepository;
import id.careerfund.api.utils.mappers.UserMapper;
import javassist.NotFoundException;
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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepo;
    private final UserClassRepository userClassRepo;

    @Override
    public Page<Class> getClasses(Collection<Long> categories, Collection<Long> institutions, String name, Double priceStart, Double priceEnd, String sort, String order) {
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
        return classRepo.findByBootcamp_Categories_IdInAndBootcamp_Institutions_IdInAndBootcamp_NameIsLikeIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(categories, institutions, name, priceStart, priceEnd, pageable);
    }

    @Override
    public Class getClassById(Long id) throws NotFoundException {
        Optional<Class> aClass = classRepo.findById(id);
        if (!aClass.isPresent()) throw new NotFoundException("Class not found");
        return aClass.get();
    }

    @Override
    public List<UserClass> getMyClasses(Principal principal) {
        return userClassRepo.findByUser(UserMapper.principalToUser(principal));
    }
}
