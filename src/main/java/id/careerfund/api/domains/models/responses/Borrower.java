package id.careerfund.api.domains.models.responses;

import id.careerfund.api.domains.entities.Balance;
import id.careerfund.api.domains.entities.Interest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Borrower {
    private String name;
    private String phoneNumber;
    private String photoPath;
    private String address;
    private Float assessmentScore;
    private List<UserClassLenderDto> userClasses = new ArrayList<>();
    private Collection<Interest> interests;
    private Balance balance;
}
