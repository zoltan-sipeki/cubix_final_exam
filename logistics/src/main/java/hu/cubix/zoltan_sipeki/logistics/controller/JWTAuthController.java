package hu.cubix.zoltan_sipeki.logistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.zoltan_sipeki.logistics.security.JWTService;
import hu.cubix.zoltan_sipeki.logistics.security.LoginDto;

@RestController
public class JWTAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDto login) throws BadCredentialsException {
        var authToken = UsernamePasswordAuthenticationToken.unauthenticated(login.username(), login.password());
        var auth = authenticationManager.authenticate(authToken);
        return jwtService.createJWT((User) auth.getPrincipal());
    }
    
}
