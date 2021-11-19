package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private String name;
    private int installment;
    private int tenor;
    private int paid;
    private double interest;
}
