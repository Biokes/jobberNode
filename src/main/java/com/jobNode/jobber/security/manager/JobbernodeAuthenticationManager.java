package com.jobNode.jobber.security.manager;

import com.jobNode.jobber.exception.JobberNodeException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.jobNode.jobber.exception.ExceptionMessages.INVALID_DETAILS;
import static com.jobNode.jobber.exception.ExceptionMessages.LOGIN_FAILED;

@Component
@AllArgsConstructor
public class JobbernodeAuthenticationManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> authenticationType =authentication.getClass();
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        if(!passwordEncoder.matches(password,userDetailsService.loadUserByUsername(email).getPassword()))
            throw new JobberNodeException(LOGIN_FAILED.getMessage());
        if(authenticationProvider.supports(authenticationType))
            return authenticationProvider.authenticate(authentication);
        throw new BadCredentialsException(INVALID_DETAILS.getMessage());
    }
}
