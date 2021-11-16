package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.Interests;


public interface InterestService {
    void saveIfNotExist(Interest interest);
    Interests get();
}
