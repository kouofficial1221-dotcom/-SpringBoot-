package com.springbootbook.ch11security.security.entity;

import java.io.Serializable;

public record SystemOperator(
        String email,
        String name,
        String role,
        String password) implements Serializable {
}
