package com.jobNode.jobber.security.services;

import com.jobNode.jobber.data.models.models.User;
import com.jobNode.jobber.security.models.SecuredUser;
import com.jobNode.jobber.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JoberNodeUserService implements UserDetailsService {
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(username);
        return new SecuredUser(user);
    }
}
