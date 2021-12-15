package id.careerfund.api.domains.models.responses;

import id.careerfund.api.domains.entities.Balance;
import id.careerfund.api.domains.entities.Interest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Borrower {
    private String name;
    private String phoneNumber;
    private String photoPath;
    private String address;
    private Float assessmentScore;
    private List<UserClassResponse> userClasses = new ArrayList<>();
    private Collection<Interest> interests;
    private Balance balance;
}
