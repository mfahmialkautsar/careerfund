package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public interface ClassService {
    Page<Class> getClasses(Collection<Long> categories, Collection<Long> institutions, String name, Double feeStart, Double feeEnd, String sort, String order);

    Class getClassById(Long id) throws NotFoundException;

    List<UserClass> getMyClasses(Principal principal);
}
