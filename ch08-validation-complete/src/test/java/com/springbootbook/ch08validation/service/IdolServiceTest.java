package com.springbootbook.ch08validation.service;

import com.springbootbook.ch08validation.persistence.entity.BloodType;
import com.springbootbook.ch08validation.persistence.entity.Idol;
import com.springbootbook.ch08validation.persistence.repository.IdolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IdolServiceTest {
    IdolService idolService;

    IdolRepository idolRepository;

    @BeforeEach
    void beforeEach() {
        // IdolRepositoryのモックを作成
        idolRepository = mock(IdolRepository.class);
        // IdolRepositoryのモックをIdolServiceに代入
        idolService = new IdolService(idolRepository);
    }

    @Nested
    @DisplayName("findById()")
    class FindByIdTest {
        @Test
        @DisplayName("存在するIDを指定すると、該当するアイドルを取得できる")
        void exists() {
            Optional<Idol> expected = Optional.of(new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A));
            doReturn(expected).when(idolRepository).selectById(anyInt());
            Optional<Idol> actual = idolService.findById(1);
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("存在しないIDを指定すると、空のOptionalが返る")
        void doesNotExist() {
            Optional<Idol> expected = Optional.empty();
            doReturn(expected).when(idolRepository).selectById(anyInt());
            Optional<Idol> actual = idolService.findById(1);
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("findByNameOrderById()")
    class FindByNameOrderByIdTest {
        @Test
        @DisplayName("名前に存在するキーワードを指定すると、該当するアイドルをすべて取得できる")
        void exists() {
            List<Idol> expected = List.of(
                    new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A),
                    new Idol(3, "ささもと　みく", LocalDate.of(2003, 3, 3), BloodType.O)
            );
            doReturn(expected).when(idolRepository).selectByNameOrderById(anyString());
            List<Idol> actual = idolService.findByNameOrderById("も");
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("空文字を指定すると、全アイドルのリストを取得できる")
        void all() {
            List<Idol> expected = List.of(
                    new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A),
                    new Idol(2, "とうの　りんか", LocalDate.of(2002, 2, 2), BloodType.B)
            );
            doReturn(expected).when(idolRepository).selectByNameOrderById(anyString());
            List<Idol> actual = idolService.findByNameOrderById("");
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("名前に存在しないキーワードを指定すると、空のリストを取得できる")
        void empty() {
            List<Idol> expected = List.of();
            doReturn(expected).when(idolRepository).selectByNameOrderById(anyString());
            List<Idol> actual = idolService.findByNameOrderById("を");
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("exists()")
    class ExistsTest {
        @Test
        @DisplayName("存在するIDを指定するとtrueが返る")
        void exists() {
            doReturn(1).when(idolRepository).countById(anyInt());
            boolean actual = idolService.exists(1);
            assertTrue(actual);
        }

        @Test
        @DisplayName("存在しないIDを指定するとfalseが返る")
        void doesNotExist() {
            doReturn(0).when(idolRepository).countById(anyInt());
            boolean actual = idolService.exists(999);
            assertFalse(actual);
        }
    }

    @Nested
    @DisplayName("join()")
    class JoinTest {
        @Test
        @DisplayName("アイドルを1件加入できる")
        void success() {
            Idol expected = new Idol(6, "ひらい　りか", LocalDate.of(2006, 6, 6), BloodType.A);
            doReturn(expected).when(idolRepository).insert(any());
            Idol actual = idolService.join(new Idol(null, "ひらい　りか", LocalDate.of(2006, 6, 6), BloodType.A));
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("fix()")
    class FixTest {
        @Test
        @DisplayName("存在するIDを指定すると、既存のアイドルを1件修正できる")
        void exists() {
            doReturn(1).when(idolRepository).update(any());
            int actual = idolService.fix(new Idol(1, "ふじもと　ももか", LocalDate.of(2007, 7, 7), BloodType.B));
            assertEquals(1, actual);
        }

        @Test
        @DisplayName("存在しないIDを指定すると、修正件数は0になる")
        void doesNotExist() {
            doReturn(0).when(idolRepository).update(any());
            int actual = idolService.fix(new Idol(999, "ふじもと　ももか", LocalDate.of(2007, 7, 7), BloodType.B));
            assertEquals(0, actual);
        }
    }

    @Nested
    @DisplayName("graduate()")
    class GraduateTest {
        @Test
        @DisplayName("存在するIDを指定すると、既存のアイドルを1件卒業できる")
        void exists() {
            doReturn(1).when(idolRepository).delete(anyInt());
            int actual = idolService.graduate(1);
            assertEquals(1, actual);
        }

        @Test
        @DisplayName("存在しないIDを指定すると、卒業件数は0になる")
        void doesNotExist() {
            doReturn(0).when(idolRepository).delete(anyInt());
            int actual = idolService.graduate(999);
            assertEquals(0, actual);
        }
    }
}
