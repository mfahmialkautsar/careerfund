package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable implements UserDetails {
    @JsonIgnore
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();

    @Column(name = "is_not_expired", nullable = false)
    private Boolean isNotExpired = true;

    @Column(name = "is_not_locked", nullable = false)
    private Boolean isNotLocked = true;

    @Column(name = "is_credentials_not_expired", nullable = false)
    private Boolean isCredentialsNotExpired = true;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    //    Forget Pass here
    @JsonIgnore
    private String verifyToken;

    @JsonIgnore
    private Date expiredVerifyToken;

    @Column(length = 100)
    private String otp;

    private Date otpExpiredDate;

    //add one to many users
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RefreshToken> refreshTokens;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "interest_id")
    private Interest interest;

}