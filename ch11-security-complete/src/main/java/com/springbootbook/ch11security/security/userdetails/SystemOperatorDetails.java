package com.springbootbook.ch11security.security.userdetails;

import com.springbootbook.ch11security.security.entity.SystemOperator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record SystemOperatorDetails(SystemOperator systemOperator) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(systemOperator.role()));
    }

    @Override
    public String getPassword() {
        return systemOperator.password();
    }

    @Override
    public String getUsername() {
        return systemOperator.email();
    }
}
