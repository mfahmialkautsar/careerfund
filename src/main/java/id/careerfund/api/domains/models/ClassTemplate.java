package id.careerfund.api.domains.models;

import id.careerfund.api.domains.entities.Bootcamp;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassTemplate {
    Bootcamp bootcamp;
    Interests interests;
}
