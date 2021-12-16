package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.careerfund.api.domains.EIdVerificationStatus;
import id.careerfund.api.domains.entities.Balance;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyProfile {
    private Long id;
    private String name;
    private String email;
    private Collection<Role> roles = new ArrayList<>();
    private String phoneNumber;
    private String address;
    private Collection<Interest> interests;
    private Balance balance;
    private String photoPath;
    private String identityCardPath;
    private String selfiePath;
    private Float assessmentScore;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long remainingDebt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long assets;
    private EIdVerificationStatus idVerificationStatus;
}
