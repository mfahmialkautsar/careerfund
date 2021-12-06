package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Institution;
import org.springframework.data.domain.Page;

public interface InstitutionService {
    Page<Institution> getInstitutions();
}
