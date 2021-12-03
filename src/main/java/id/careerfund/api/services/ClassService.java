package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.UserClass;

import java.security.Principal;
import java.util.List;

public interface ClassService {
    List<Class> getClasses();
    List<UserClass> getMyClasses(Principal principal);
}
