package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Institution;
import id.careerfund.api.repositories.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {
    private final InstitutionRepository institutionRepo;

    @Override
    public Page<Institution> getInstitutions() {
        Pageable pageable = Pageable.unpaged();
        return institutionRepo.findAll(pageable);
    }
}
