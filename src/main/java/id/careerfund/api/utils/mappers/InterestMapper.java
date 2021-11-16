package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.InterestsResponse;

import java.util.ArrayList;
import java.util.List;

public final class InterestMapper {
    public static InterestsResponse interestsToResponses(List<Interest> interests) {
        InterestsResponse interestsResponse = new InterestsResponse();
        interestsResponse.setInterests(interests);
        return interestsResponse;
    }
}
