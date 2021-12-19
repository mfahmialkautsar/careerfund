package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.careerfund.api.domains.entities.Class;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClassBorrowerDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long id;
    private Class aClass;
    private LoanBorrowerDto loan;
    private Double score;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean dpPaid;

}
