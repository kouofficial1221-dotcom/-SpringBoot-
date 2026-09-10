package com.springbootbook.ch11security.security.userdetails;

import com.springbootbook.ch11security.security.entity.SystemOperator;
import com.springbootbook.ch11security.security.repository.SystemOperatorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SystemOperatorDetailsService implements UserDetailsService {
    private final SystemOperatorRepository systemOperatorRepository;

    public SystemOperatorDetailsService(SystemOperatorRepository systemOperatorRepository) {
        this.systemOperatorRepository = systemOperatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<SystemOperator> operatorOptional = systemOperatorRepository.selectByEmail(email);
        SystemOperatorDetails userDetails = operatorOptional.map(user -> new SystemOperatorDetails(user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " was not found"));
        return userDetails;
    }
}
