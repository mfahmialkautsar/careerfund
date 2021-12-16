package id.careerfund.api.services;

import id.careerfund.api.domains.models.responses.Interests;
import id.careerfund.api.repositories.InterestRepository;
import id.careerfund.api.utils.mappers.InterestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {
    private final InterestRepository interestRepository;

    @Override
    public Interests get() {
        return InterestMapper.interestsToModel(interestRepository.findAll());
    }
}
