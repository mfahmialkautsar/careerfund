package id.careerfund.api.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInterest {
    @NotNull(message = "Cannot null")
    private List<Long> interestIds;
}
