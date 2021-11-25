package id.careerfund.api.services;

import id.careerfund.api.domains.models.NewBootcamp;

public interface BootcampService {
    Boolean bootcampIsExist(String name);

    void saveBootcamp(NewBootcamp newBootcamp);

    void saveBootcampIfNotExist(NewBootcamp newBootcamp);
}
