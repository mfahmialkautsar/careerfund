package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.careerfund.api.domains.EIdVerificationStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Please input email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @NotEmpty(message = "Password is mandatory")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Lob
    @Column(name = "identity_card_path")
    private String identityCardPath;

    @Lob
    @Column(name = "selfie_path")
    private String selfiePath;

    @Lob
    @Column(name = "photo_path")
    private String photoPath;

    @Lob
    @Column(name = "address")
    private String address;

    @Column(name = "assessment_score")
    private Float assessmentScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_verification_status")
    private EIdVerificationStatus idVerificationStatus;

    @JsonIgnore
    @Column(name = "is_not_expired", nullable = false)
    private Boolean isNotExpired = true;

    @JsonIgnore
    @Column(name = "is_not_locked", nullable = false)
    private Boolean isNotLocked = true;

    @JsonIgnore
    @Column(name = "is_credentials_not_expired", nullable = false)
    private Boolean isCredentialsNotExpired = true;

    @JsonIgnore
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = false;

    @JsonIgnore
    @OneToMany(mappedBy = "borrower", orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<UserClass> userClasses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "lender", orphanRemoval = true)
    private List<Funding> fundings = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isNotExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isNotLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNotExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    // Forget Pass here
    @JsonIgnore
    private String verifyToken;

    @JsonIgnore
    @Column(length = 100)
    private String otp;

    @JsonIgnore
    private LocalDateTime otpExpiredDate;

    // add one to many users
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RefreshToken> refreshTokens;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_interests", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Collection<Interest> interests;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "balance_id")
    private Balance balance;
}