package com.springbootbook.ch11security.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login  // フォームによるログインを有効化
                .loginPage("/login")  // ログイン画面のURLは/login
                .defaultSuccessUrl("/")  // ログイン後は/にリダイレクトする
                .failureUrl("/login?error")  // 認証に失敗したら/login?errorにリダイレクトする
                .permitAll()  // ログイン画面には未ログインでもアクセスOKにする
        ).authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**").permitAll()  // /css/**は未ログインでもアクセスOKにする
                .requestMatchers(HttpMethod.POST, "/idol/graduate/**").hasRole("MANAGER")  // /idol/graduate/**へのPOSTはROLE_MANAGERのみOK
                .anyRequest().authenticated()  // その他のURLはログイン中であれば誰でもOK
        ).logout(logout -> logout
                .logoutSuccessUrl("/login")  // ログアウト成功後は/loginにリダイレクトする
        );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
