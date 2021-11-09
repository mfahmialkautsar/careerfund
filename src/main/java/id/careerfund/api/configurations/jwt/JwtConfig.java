package id.careerfund.api.configurations.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
    private String secret_key;
    private String token_prefix;
    private Integer token_expired_days;

    public JwtConfig() {
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getToken_prefix() {
        return token_prefix;
    }

    public void setToken_prefix(String token_prefix) {
        this.token_prefix = token_prefix;
    }

    public Integer getToken_expired_days() {
        return token_expired_days;
    }

    public void setToken_expired_days(Integer token_expired_days) {
        this.token_expired_days = token_expired_days;
    }
}

