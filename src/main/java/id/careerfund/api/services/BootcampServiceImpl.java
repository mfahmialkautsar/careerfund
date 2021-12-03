package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.entities.UserBootcamp;
import id.careerfund.api.repositories.BootcampRepository;
import id.careerfund.api.repositories.UserBootcampRepository;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BootcampServiceImpl implements BootcampService{
    private final BootcampRepository bootcampRepo;
    private final UserBootcampRepository enrolledBootcampRepo;

    @Override
    public List<Bootcamp> getBootcamps() {
        return bootcampRepo.findAll();
    }

    @Override
    public List<UserBootcamp> getMyBootcamps(Principal principal) {
        return enrolledBootcampRepo.findByUser(UserMapper.principalToUser(principal));
    }
}
