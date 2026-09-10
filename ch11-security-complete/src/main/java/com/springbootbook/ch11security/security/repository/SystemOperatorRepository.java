package com.springbootbook.ch11security.security.repository;

import com.springbootbook.ch11security.security.entity.SystemOperator;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SystemOperatorRepository {
    private final JdbcClient jdbcClient;

    public SystemOperatorRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<SystemOperator> selectByEmail(String email) {
        Optional<SystemOperator> operatorOptional = jdbcClient.sql("""
                        SELECT email, name, role, password FROM system_operator
                        WHERE email = :email
                        """)
                .param("email", email)
                .query(new DataClassRowMapper<>(SystemOperator.class))
                .optional();
        return operatorOptional;
    }
}
