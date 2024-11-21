package hu.cubix.zoltan_sipeki.logistics.security;

import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Service
public class JWTService {

    private static final String USERNAME = "username";

    private static final String ROLES = "roles";

    @Autowired
    private JWTConfig jwtConfig;

    private Algorithm algorithm;

    private JWTVerifier verifier;

    @PostConstruct
    public void init() throws Exception {
        var secret = Base64.getDecoder().decode(jwtConfig.getSecret());
        algorithm = (Algorithm) Algorithm.class.getDeclaredMethod(jwtConfig.getAlgorithm(), byte[].class)
                .invoke(null, secret);
        verifier = JWT.require(algorithm).build();
    }

    public String createJWT(User user) {
        var jwt = JWT.create();
        jwt.withClaim(USERNAME, user.getUsername());
        jwt.withClaim(ROLES, user.getAuthorities().stream().map(a -> a.getAuthority()).toList());
        jwt.withIssuer(jwtConfig.getIssuer());
        jwt.withExpiresAt(Instant.now().plusMillis(jwtConfig.getExpiration().toMillis()));

        return jwt.sign(algorithm);
    }

    public DecodedJWT verifyJWT(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }

    public User parseJWT(DecodedJWT jwt) {
        var username = jwt.getClaim(USERNAME).asString();
        var roles = jwt.getClaim(ROLES).asList(String.class).stream().map(r -> new SimpleGrantedAuthority(r)).toList();
        return new User(username, "", roles);
    }

}
