package com.springbootbook.ch11security.security.userdetails;

import com.springbootbook.ch11security.security.entity.SystemOperator;
import com.springbootbook.ch11security.security.repository.SystemOperatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SystemOperatorDetailsServiceTest {

    SystemOperatorRepository systemOperatorRepository;

    SystemOperatorDetailsService systemOperatorDetailsService;

    @BeforeEach
    void beforeEach() {
        systemOperatorRepository = mock(SystemOperatorRepository.class);
        systemOperatorDetailsService = new SystemOperatorDetailsService(systemOperatorRepository);
    }

    @Nested
    @DisplayName("loadUserByUsername()")
    class LoadUserByUsernameTest {
        @Test
        @DisplayName("存在するメールアドレスを指定すると、該当するオペレーター情報を取得できる")
        void success() {
            doReturn(Optional.of(new SystemOperator("operator@idol.com", "オペレーター", "ROLE_OPERATOR", "operator")))
                    .when(systemOperatorRepository).selectByEmail(anyString());
            UserDetails actual = systemOperatorDetailsService.loadUserByUsername("operator@idol.com");
            assertEquals(new SystemOperatorDetails(new SystemOperator("operator@idol.com", "オペレーター", "ROLE_OPERATOR", "operator")), actual);
        }

        @Test
        @DisplayName("存在しないメールアドレスを指定するとUsernameNotFoundException")
        void notFound() {
            doReturn(Optional.empty())
                    .when(systemOperatorRepository).selectByEmail(anyString());
            assertThrows(UsernameNotFoundException.class, () -> systemOperatorDetailsService.loadUserByUsername("xxxxx"));
        }
    }
}
