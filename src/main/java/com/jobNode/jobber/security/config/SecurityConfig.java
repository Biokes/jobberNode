package com.jobNode.jobber.security.config;

import com.jobNode.jobber.security.filters.JobberNodeAuthenticationFilter;
import com.jobNode.jobber.security.filters.JobberNodeAuthourizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.jobNode.jobber.data.models.enums.Role.*;
import static com.jobNode.jobber.security.utils.Utils.CUSTOMER_END_POINTS;
import static com.jobNode.jobber.security.utils.Utils.END_POINTS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager manager;
    private final JobberNodeAuthourizationFilter authourizationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var authenticationFilter = new JobberNodeAuthenticationFilter(manager);
            authenticationFilter.setFilterProcessesUrl("api/v1/jobberNode/login");
       return  httpSecurity.csrf(AbstractHttpConfigurer::disable)
               .cors(AbstractHttpConfigurer::disable)
               .addFilterAt(new JobberNodeAuthenticationFilter(manager), BasicAuthenticationFilter.class)
                .addFilterBefore(new JobberNodeAuthourizationFilter(), JobberNodeAuthenticationFilter.class)
                .sessionManagement(customizer->customizer.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(c->c.requestMatchers(POST,"api/v1/jobberNode/customer/register",
                                            "api/v1/jobbberNode/provider/register").permitAll()
                .requestMatchers(CUSTOMER_END_POINTS).hasAuthority(CUSTOMER.name())
                .requestMatchers("api/v1/jobberNode/provider/**").hasAuthority(PROVIDER.name()))
                .authorizeHttpRequests(c->c.requestMatchers("api/v1/jobberode/admin/**").hasAuthority(ADMIN.name()))
                .build();
    }

}
