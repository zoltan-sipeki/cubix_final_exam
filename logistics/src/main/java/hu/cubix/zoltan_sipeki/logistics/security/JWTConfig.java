package hu.cubix.zoltan_sipeki.logistics.security;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

    private String algorithm;

    private String secret;

    private Duration expiration;

    private String issuer;

    public JWTConfig(String algorithm, String secret, Duration expiration, String issuer) {
        this.algorithm = algorithm;
        this.secret = secret;
        this.expiration = expiration;
        this.issuer = issuer;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getSecret() {
        return secret;
    }

    public Duration getExpiration() {
        return expiration;
    }

    public String getIssuer() {
        return issuer;
    }
}
