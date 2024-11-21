package hu.cubix.zoltan_sipeki.logistics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String ADDRESS_MANAGER = "AddressManager";

    private static final String TRANSPORT_MANAGER = "TransportManager";

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var user1 = User.builder()
                .username("user1")
                .password("password1")
                .passwordEncoder(password -> passwordEncoder.encode(password))
                .authorities("ROLE_" + ADDRESS_MANAGER).build();

        var user2 = User.builder()
                .username("user2")
                .password("password2")
                .passwordEncoder(password -> passwordEncoder.encode(password))
                .authorities("ROLE_" + TRANSPORT_MANAGER).build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthFilter jwtAuthFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(am -> am
                        .requestMatchers(HttpMethod.POST, "/api/addresses/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/addresses/**").hasRole(ADDRESS_MANAGER)
                        .requestMatchers(HttpMethod.PUT, "/api/addresses/**").hasRole(ADDRESS_MANAGER)
                        .requestMatchers(HttpMethod.DELETE, "/api/addresses/**").hasRole(ADDRESS_MANAGER)
                        .requestMatchers(HttpMethod.POST, "/api/transportPlans/**").hasRole(TRANSPORT_MANAGER)
                        .anyRequest().permitAll())
                .exceptionHandling(e -> e.authenticationEntryPoint((request, response, exception) -> response
                        .sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase())))
                .build();
    }
}
