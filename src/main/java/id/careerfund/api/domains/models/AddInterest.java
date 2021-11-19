package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInterest {
    @NotNull(message = "Cannot null")
    private Long interestId;
}
