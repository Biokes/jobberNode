package com.jobNode.jobber.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.jobNode.jobber.exception.ExceptionMessages.INVALID_DETAILS;

@Component
@AllArgsConstructor
public class JobbernodeAuthenticationManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> authenticationType =authentication.getClass();
        if(authenticationProvider.supports(authenticationType))
            return authenticationProvider.authenticate(authentication);
        throw new BadCredentialsException(INVALID_DETAILS.getMessage());
    }
}
