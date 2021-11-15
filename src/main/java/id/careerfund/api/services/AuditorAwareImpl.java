package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AuditorAwareImpl implements AuditorAware<User> {
    @Autowired
    private UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {
        log.info("Auditing");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            String email = authentication.getName();
            log.info("By {}", email);
            return Optional.of(userService.getUser(email));
        }
        return Optional.empty();
    }
}
