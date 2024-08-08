package com.jobNode.jobber.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNode.jobber.dto.request.LoginRequest;
import com.jobNode.jobber.dto.response.ApiResponse;
import com.jobNode.jobber.dto.response.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static com.jobNode.jobber.security.utils.Utils.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
public class JobberNodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
            try{
                InputStream requestStream = request.getInputStream();
                LoginRequest loginRequest = objectMapper.readValue(requestStream, LoginRequest.class);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),loginRequest.getPassword());
                Authentication authenticationResult = authenticationManager.authenticate(authentication);
                SecurityContextHolder.getContext().setAuthentication(authenticationResult);
                return authenticationResult;
            }
            catch(IOException exception){
                throw new RuntimeException(exception.getMessage());
            }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authenticationResult)
            throws IOException, ServletException {
        Collection<? extends GrantedAuthority> roles= authenticationResult.getAuthorities();
        List<String> role =roles.stream().map(GrantedAuthority::getAuthority).toList();
        String token = generateAccessToken(role);
        LoginResponse loginResponse = LoginResponse.builder().message("Successfully Logged in")
                .token(token).build();
        ApiResponse<LoginResponse> apiResponse = ApiResponse.<LoginResponse>builder().data(loginResponse)
                                                    .success(true).status(OK).code(OK.value()).build();
        response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
        response.flushBuffer();
        chain.doFilter(request,response);
    }
    public static String generateAccessToken(List<String> authorities){
        String token = JWT.create()
                .withClaim(ROLES, authorities)
                .withExpiresAt(Instant.now().plusSeconds(60*60*72))
                .withIssuer(APP_NAME)
                .sign(Algorithm.HMAC512(SECRET));
        return token;


    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        LoginResponse loginResponse = LoginResponse.builder().message("Login Failed").build();
        ApiResponse<LoginResponse> apiResponse = ApiResponse.<LoginResponse>builder()
                .data(loginResponse).code(UNAUTHORIZED.value())
                .status(UNAUTHORIZED).success(false).build();
        response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
        response.flushBuffer();
    }

}
