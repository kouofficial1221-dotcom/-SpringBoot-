package com.springbootbook.ch11security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodingMain {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed1 = encoder.encode("operator");
        System.out.println(hashed1);
        String hashed2 = encoder.encode("manager");
        System.out.println(hashed2);
        System.out.println(encoder.matches("operator", hashed1));
        System.out.println(encoder.matches("manager", hashed2));
    }
}
