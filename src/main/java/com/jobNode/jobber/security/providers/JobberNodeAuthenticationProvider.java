package com.jobNode.jobber.security.providers;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class JobberNodeAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(passwordEncoder.matches(password,userDetails.getPassword()))
            return new UsernamePasswordAuthenticationToken(null, null, userDetails.getAuthorities());
        throw new BadCredentialsException("Invalid details provided");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
