package com.jobNode.jobber.security.config;

import com.jobNode.jobber.security.filters.JobberNodeAuthenticationFilter;
import com.jobNode.jobber.security.filters.JobberNodeAuthourizationFilter;
import com.jobNode.jobber.security.manager.JobbernodeAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.jobNode.jobber.data.models.enums.Role.*;
import static com.jobNode.jobber.security.utils.Utils.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JobbernodeAuthenticationManager manager;
    private final JobberNodeAuthourizationFilter authourizationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return  httpSecurity.csrf(AbstractHttpConfigurer::disable)
               .cors(Customizer.withDefaults())
               .addFilterAt(new JobberNodeAuthenticationFilter(manager), BasicAuthenticationFilter.class)
               .addFilterBefore(authourizationFilter, JobberNodeAuthenticationFilter.class)
               .sessionManagement(customizer->customizer.sessionCreationPolicy(STATELESS))
               .authorizeHttpRequests(c->c.requestMatchers(POST, PUBLIC_URLS).permitAll())
               .authorizeHttpRequests(c->c.requestMatchers(CUSTOMER_END_POINTS).hasAuthority(CUSTOMER.name()))
               .authorizeHttpRequests(c->c.requestMatchers(PROVIDER_URL).hasAuthority(PROVIDER.name()))
               .authorizeHttpRequests(c->c.requestMatchers(ADMIN_URL).hasAuthority(ADMIN.name()))
               .build();
    }
    @Bean
    public UsernamePasswordAuthenticationFilter authFilter(){
        var authenticationFilter = new JobberNodeAuthenticationFilter(manager);
        authenticationFilter.setFilterProcessesUrl("/api/v1/jobberNode/login");
        authenticationFilter.setAuthenticationManager(manager);
        return authenticationFilter;
    }
}
