package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MyClass {
    private final String institution;
    private final String program;
    private final String logoPath;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Double score;
}
