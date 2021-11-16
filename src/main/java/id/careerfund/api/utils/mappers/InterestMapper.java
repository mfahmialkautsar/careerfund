package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.Interests;

import java.util.List;

public final class InterestMapper {
    public static Interests interestsToModel(List<Interest> interests) {
        Interests interestsResponse = new Interests();
        interestsResponse.setInterests(interests);
        return interestsResponse;
    }
}
