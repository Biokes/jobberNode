package com.jobNode.jobber.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.jobNode.jobber.security.utils.Utils.END_POINTS;
import static com.jobNode.jobber.security.utils.Utils.JWT_PREFIX;

@Component
public class JobberNodeAuthourizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getServletPath();
        boolean isRequestPath = END_POINTS.contains(requestPath);
        if(isRequestPath){
            filterChain.doFilter(request,response);
            return;
        }
        String authourization =request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authourization!= null) {
            String token = authourization.substring(JWT_PREFIX.length()).strip();
            JWTVerifier jwtVerifier = getJwtVerifier();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles")
                    .asList(SimpleGrantedAuthority.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private static JWTVerifier getJwtVerifier() {
        return JWT.require(Algorithm.HMAC512("secret".getBytes()))
                .withIssuer("JobberNode")
                .withClaimPresence("roles")
                .build();
    }
}
