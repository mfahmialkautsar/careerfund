package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AuditorAwareImpl implements AuditorAware<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("audit");
        if (authentication != null) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            log.info(email);
            if (user != null)
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
