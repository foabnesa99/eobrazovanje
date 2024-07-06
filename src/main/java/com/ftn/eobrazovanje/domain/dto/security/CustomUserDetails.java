package com.ftn.eobrazovanje.domain.dto.security;

import com.ftn.eobrazovanje.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomUserDetails implements UserDetails, Principal {

    private String name;
    private List<GrantedAuthority> authorities;
    private String password;
    private String username;
    private String lastName;
    private boolean accountActive;


    public CustomUserDetails(User user) {
        this.name = user.getFirstName();
        this.authorities = user.getAuthorities();
        this.password = user.getPassword();
        this.username = user.getEmail();
        this.lastName = user.getLastName();
        this.accountActive = user.isActive() && !user.isDeleted();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return accountActive;
    }

    @Override
    public boolean isEnabled() {
        return accountActive;
    }
}
