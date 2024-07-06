package com.ftn.eobrazovanje.service.security;

import com.ftn.eobrazovanje.domain.dto.security.CustomUserDetails;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username);
        return new CustomUserDetails(user);
    }
}
