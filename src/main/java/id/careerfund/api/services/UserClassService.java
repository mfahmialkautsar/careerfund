package id.careerfund.api.services;

import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.requests.UserClassRequest;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

public interface UserClassService {

    UserClass registerClass(Principal principal, UserClassRequest userClassRequest) throws EntityNotFoundException;

    List<UserClass> getMyClasses(Principal principal);

    UserClass getMyClassById(Principal principal, Long id) throws AccessDeniedException;
}
