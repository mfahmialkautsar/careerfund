package id.careerfund.api.configurations;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
@NoArgsConstructor
public class JwtConfig {
    private String secret_key;
    private String token_prefix;
    private Integer token_expired_days;
}

