package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    private SimpleUser user;
    private List<Loan> loans = new ArrayList<>();
}
