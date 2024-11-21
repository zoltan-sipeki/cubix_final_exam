package hu.cubix.zoltan_sipeki.logistics.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer ";

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.substring(BEARER.length());
        try {
            var decodedJWT = jwtService.verifyJWT(token);
            var user = jwtService.parseJWT(decodedJWT);
            var securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(
                    UsernamePasswordAuthenticationToken.authenticated(user, "", user.getAuthorities()));
            SecurityContextHolder.setContext(securityContext);

            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            e.printStackTrace();
        }

    }

}