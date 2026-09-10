package com.springbootbook.ch11security.persistence.repository;

import com.springbootbook.ch11security.persistence.entity.BloodType;
import com.springbootbook.ch11security.persistence.entity.Idol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class IdolRepositoryTest {
    @Autowired
    IdolRepository idolRepository;

    @Autowired
    JdbcClient jdbcClient;

    @Nested
    @DisplayName("selectById()")
    class SelectByIdTest {
        @Test
        @DisplayName("存在するIDを指定すると、該当するアイドルを取得できる")
        void exists() {
            Optional<Idol> actual = idolRepository.selectById(1);
            // 検索結果が想定通りか確認
            assertEquals(new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A),
                    actual.get());
        }

        @Test
        @DisplayName("存在しないIDを指定すると、空のOptionalが返る")
        void empty() {
            Optional<Idol> actual = idolRepository.selectById(999);
            // 検索結果が想定通りか確認
            assertEquals(Optional.empty(), actual);
        }
    }

    @Nested
    @DisplayName("selectByNameOrderById()")
    class SelectByNameOrderByIdTest {
        @Test
        @DisplayName("名前に存在するキーワードを指定すると、該当するアイドルをすべて取得できる")
        void exists() {
            List<Idol> actual = idolRepository.selectByNameOrderById("も");
            // 検索結果が想定通りか確認
            assertEquals(List.of(
                    new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A),
                    new Idol(3, "ささもと　みく", LocalDate.of(2003, 3, 3), BloodType.O)
            ), actual);
        }

        @Test
        @DisplayName("空文字を指定すると、全アイドルのリストを取得できる")
        void all() {
            List<Idol> actual = idolRepository.selectByNameOrderById("");
            // 検索結果の件数が想定通りか確認
            // 各フィールドの値は前のテストで確認できているので、件数のみでOK
            assertEquals(5, actual.size());
        }

        @Test
        @DisplayName("名前に存在しないキーワードを指定すると、空のリストを取得できる")
        void empty() {
            List<Idol> actual = idolRepository.selectByNameOrderById("を");
            // 検索結果が想定通りか確認
            assertEquals(List.of(), actual);
        }
    }

    @Nested
    @DisplayName("countById()")
    class CountByIdTest {
        @Test
        @DisplayName("存在するIDを指定すると1が返る")
        void exists() {
            int actual = idolRepository.countById(1);
            // 件数が想定通りか確認
            assertEquals(1, actual);
        }

        @Test
        @DisplayName("存在しないIDを指定すると0が返る")
        void doesNotExist() {
            int actual = idolRepository.countById(999);
            // 件数が想定通りか確認
            assertEquals(0, actual);
        }
    }

    @Nested
    @DisplayName("update()")
    class UpdateTest {
        @Test
        @DisplayName("存在するIDを指定すると、既存のアイドルを1件更新できる")
        void exists() {
            Idol targetIdol = new Idol(1, "ふじもと　ももか", LocalDate.of(2007, 7, 7), BloodType.B);
            int actual = idolRepository.update(targetIdol);
            Optional<Idol> idolOptional = idolRepository.selectById(1);
            assertAll(
                    // 更新件数が想定通りか確認
                    () -> assertEquals(1, actual),
                    // データベースの内容が想定通り更新されているか確認
                    () -> assertEquals(targetIdol, idolOptional.get())
            );
        }

        @Test
        @DisplayName("存在しないIDを指定すると、更新件数は0になる")
        void doesNotExist() {
            int actual = idolRepository.update(new Idol(999, "ふじもと　ももか", LocalDate.of(2007, 7, 7), BloodType.B));
            assertAll(
                    // 更新件数が想定通りか確認
                    () -> assertEquals(0, actual),
                    // 指定したIDがデータベースに存在しないことを確認
                    () -> assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcClient, "idol", "id = 999"))
            );
        }
    }

    @Nested
    @DisplayName("delete()")
    class DeleteTest {
        @Test
        @DisplayName("存在するIDを指定すると、既存のアイドルを1件削除できる")
        void exists() {
            int actual = idolRepository.delete(1);
            assertAll(
                    // 削除件数が想定通りか確認
                    () -> assertEquals(1, actual),
                    // ID=1がデータベースに存在しないことを確認
                    () -> assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcClient, "idol", "id = 1"))
            );
        }

        @Test
        @DisplayName("存在しないIDを指定すると、削除件数は0になる")
        void doesNotExists() {
            int actual = idolRepository.delete(999);
            assertAll(
                    // 削除件数が想定通りか確認
                    () -> assertEquals(0, actual),
                    // データベース内の件数が削除前と同じであることを確認
                    () -> assertEquals(5, JdbcTestUtils.countRowsInTable(jdbcClient, "idol"))
            );
        }
    }

    @Nested
    @DisplayName("insert()")
    class InsertTest {
        @Test
        @DisplayName("アイドルを1件追加できる")
        void success() {
            Idol actual = idolRepository.insert(new Idol(null, "ひらい　りか", LocalDate.of(2006, 6, 6), BloodType.A));
            Idol expected = new Idol(6, "ひらい　りか", LocalDate.of(2006, 6, 6), BloodType.A);
            Optional<Idol> idolOptional = idolRepository.selectById(6);
            assertAll(
                    // 戻り値が想定通りか確認
                    () -> assertEquals(expected, actual),
                    // ID=6でデータベースに登録されていることを確認
                    () -> assertEquals(expected, idolOptional.get())
            );
        }
    }
}
