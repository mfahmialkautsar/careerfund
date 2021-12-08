package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Collection;

public interface ClassService {
    Page<Class> getClasses(Principal principal, Collection<Long> categories, Collection<Long> institutions, String name, Double priceStart, Double priceEnd, String sort, String order);

    Class getClassById(Principal principal, Long id) throws NotFoundException;
}
