package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.entities.UserBootcamp;

import java.security.Principal;
import java.util.List;

public interface BootcampService {
    List<Bootcamp> getBootcamps();
    List<UserBootcamp> getMyBootcamps(Principal principal);
}
