package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.Interests;
import id.careerfund.api.domains.models.NewInterest;


public interface InterestService {
    void saveInterest(NewInterest newInterest);
    void saveIfNotExist(NewInterest newInterest);
    Interests get();
}
