package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.Interests;
import id.careerfund.api.domains.models.NewInterest;
import id.careerfund.api.repositories.InterestRepository;
import id.careerfund.api.utils.mappers.InterestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {
    private final InterestRepository interestRepository;

    @Override
    public void saveInterest(NewInterest newInterest) {
        Interest interest = new Interest();

        interest.setName(newInterest.getName());
        interestRepository.save(interest);
    }

    @Override
    public void saveIfNotExist(NewInterest newInterest) {
        Interest interest = interestRepository.findByName(newInterest.getName());
        if (ObjectUtils.isEmpty(interest)) {
            saveInterest(newInterest);
        }
    }

    @Override
    public Interests get() {
        return InterestMapper.interestsToModel(interestRepository.findAll());
    }
}
