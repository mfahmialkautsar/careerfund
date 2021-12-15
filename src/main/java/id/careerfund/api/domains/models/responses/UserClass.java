package id.careerfund.api.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.FinancialTransaction;
import id.careerfund.api.domains.entities.Loan;
import id.careerfund.api.domains.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@Getter
public class UserClass {
    private Long id;
    private Class aClass;
    private Loan loan;
    private Double score;
    private User user;
    private FinancialTransaction transferedToBootcamp;
}
