package id.careerfund.api.utils.mappers;

import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.models.responses.UserClassResponse;

import java.util.ArrayList;
import java.util.List;

public final class UserClassMapper {
    public static UserClassResponse entityToResponse(UserClass userClass) {
        UserClassResponse userClassResponse = new UserClassResponse();
        userClassResponse.setId(userClass.getId());
        userClassResponse.setAClass(userClass.getAClass());
        userClassResponse.setLoan(userClass.getLoan());
        userClassResponse.setScore(userClass.getScore());
        userClassResponse.setTransferredToBootcamp(userClass.getTransferredToBootcamp());
        userClassResponse.setIsDpPaid(userClass.getIsDpPaid());
        return userClassResponse;
    }

    public static List<UserClassResponse> entitiesToResponses(List<UserClass> userClasses) {
        List<UserClassResponse> userClassResponses = new ArrayList<>();
        for (UserClass userClass : userClasses) {
            userClassResponses.add(entityToResponse(userClass));
        }
        return userClassResponses;
    }
}
