package com.springbootbook.ch11security.security.userdetails;

import com.springbootbook.ch11security.security.entity.SystemOperator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record SystemOperatorDetails()  {

}
