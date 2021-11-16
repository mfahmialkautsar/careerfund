package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.Interests;
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
    public void saveIfNotExist(Interest interest) {
        if (!interestRepository.findById(interest.getId()).isPresent()) {
            interestRepository.save(interest);
        }
    }

    @Override
    public Interests get() {
        return InterestMapper.interestsToModel(interestRepository.findAll());
    }
}
