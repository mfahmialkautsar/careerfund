package id.careerfund.api.services;

import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.requests.UserClassRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

public interface UserClassService {

    UserClass registerClass(Principal principal, UserClassRequest userClassRequest) throws EntityNotFoundException, RequestRejectedException;

    List<UserClass> getMyClasses(Principal principal);

    UserClass getMyClassById(Principal principal, Long id) throws AccessDeniedException;
}
