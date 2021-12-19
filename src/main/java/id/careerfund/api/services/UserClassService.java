package id.careerfund.api.services;

import id.careerfund.api.domains.models.requests.PayMyLoan;
import id.careerfund.api.domains.models.requests.UserClassRequest;
import id.careerfund.api.domains.models.responses.UserClassBorrowerDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

public interface UserClassService {

    UserClassBorrowerDto registerClass(Principal principal, UserClassRequest userClassRequest)
            throws EntityNotFoundException, RequestRejectedException;

    List<UserClassBorrowerDto> getMyClasses(Principal principal);

    UserClassBorrowerDto getMyClassById(Principal principal, Long id) throws AccessDeniedException;

    UserClassBorrowerDto payMyClass(Principal principal, Long id, PayMyLoan payMyLoan)
            throws AccessDeniedException, RequestRejectedException, EntityNotFoundException;
}
