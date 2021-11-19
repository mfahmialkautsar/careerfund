package id.careerfund.api.domains.models;

import id.careerfund.api.domains.entities.Interest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyInterest {
    private final Long user;
    private final Interest interest;
}
