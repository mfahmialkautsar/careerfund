package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewClass {
    private String bootcamp;

    private String interest;

    private int duration;

    private int quota;

    private int fee;
}
