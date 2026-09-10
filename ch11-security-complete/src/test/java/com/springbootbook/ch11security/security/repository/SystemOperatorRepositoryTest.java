package com.springbootbook.ch11security.security.repository;

import com.springbootbook.ch11security.security.entity.SystemOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SystemOperatorRepositoryTest {

    @Autowired
    SystemOperatorRepository systemOperatorRepository;

    @Nested
    @DisplayName("selectByEmail()")
    class SelectByEmailTest {
        @Test
        @DisplayName("存在するメールアドレスを指定すると、該当するオペレーター情報を取得できる")
        void success() {
            Optional<SystemOperator> actual = systemOperatorRepository.selectByEmail("operator@idol.com");
            assertEquals(new SystemOperator("operator@idol.com", "オペレーター", "ROLE_OPERATOR", "$2a$10$wjCrj14XAZ6qze0rFAGCZO16WXOXE55anHjVEvDfwS9rd0aEA.81."),
                    actual.get());
        }

        @Test
        @DisplayName("存在しないメールアドレスを指定すると、空のOptionalが返る")
        void empty() {
            Optional<SystemOperator> actual = systemOperatorRepository.selectByEmail("xxxxx");
            assertTrue(actual.isEmpty());
        }
    }
}
