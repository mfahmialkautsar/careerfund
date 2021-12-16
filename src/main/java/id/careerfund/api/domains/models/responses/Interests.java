package id.careerfund.api.domains.models.responses;

import id.careerfund.api.domains.entities.Interest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interests {
    private List<Interest> interests;
}
