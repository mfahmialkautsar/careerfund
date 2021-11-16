package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateInterest {
    @Nullable
    private List<Long> adds;
    @Nullable
    private List<Long> deletes;
}
