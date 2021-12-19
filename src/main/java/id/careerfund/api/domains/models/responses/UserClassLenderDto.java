package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.careerfund.api.domains.entities.Class;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClassLenderDto implements Serializable {
    private Long id;
    private Class aClass;
    private Double score;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean dpPaid;

}
