package am.basic.notificator.auth.service;


import am.basic.notificator.model.User;
import am.basic.notificator.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CrmService crmService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = crmService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new LockedException("User is unverified");
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), simpleGrantedAuthorities);
    }
}
