package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.models.NewBootcamp;

import java.util.List;

public interface BootcampService {
    Boolean bootcampIsExist(String name);

    void saveBootcamp(NewBootcamp newBootcamp);

    void saveBootcampIfNotExist(NewBootcamp newBootcamp);

    List<Bootcamp> getBootcamp();
}
