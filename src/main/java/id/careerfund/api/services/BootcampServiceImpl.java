package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.models.NewBootcamp;
import id.careerfund.api.repositories.BootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BootcampServiceImpl implements BootcampService{
    private final BootcampRepository bootcampRepo;

    @Override
    public Boolean bootcampIsExist(String name) {
        return !ObjectUtils.isEmpty(bootcampRepo.findByName(name));
    }

    @Override
    public void saveBootcamp(NewBootcamp newBootcamp) {
        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setName(newBootcamp.getName());
        bootcampRepo.save(bootcamp);
    }

    @Override
    public void saveBootcampIfNotExist(NewBootcamp newBootcamp) {
        if (!bootcampIsExist(newBootcamp.getName())) {
            saveBootcamp(newBootcamp);
        }
    }

    @Override
    public List<Bootcamp> getBootcamp() {
        return bootcampRepo.findAll();
    }
}
