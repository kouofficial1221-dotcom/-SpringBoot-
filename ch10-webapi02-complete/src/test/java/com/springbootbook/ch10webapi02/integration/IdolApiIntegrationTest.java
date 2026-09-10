package com.springbootbook.ch10webapi02.integration;

import com.springbootbook.ch10webapi02.persistence.entity.BloodType;
import com.springbootbook.ch10webapi02.persistence.entity.Idol;
import com.springbootbook.ch10webapi02.persistence.repository.IdolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
@AutoConfigureRestTestClient
public class IdolApiIntegrationTest {
    @Autowired
    RestTestClient restTestClient;

    @Autowired
    IdolRepository idolRepository;

    @Nested
    @DisplayName("アイドルのID検索")
    class IdSearchTest {
        @Test
        @DisplayName("存在するIDを指定すると、該当するアイドルを取得できる")
        void success() {
            // /api/idols/1 にGETリクエストしたら
            restTestClient.get().uri("/api/idols/1")
                    .exchange()
                    // ステータスコードが200 OKになるはず
                    .expectStatus().isOk()
                    // レスポンスボディが次のようなアイドルJSONになるはず
                    .expectBody().json("""
                            {
                                "id" : 1,
                                "name" : "ふじの　もも",
                                "birthday" : "2001-01-01",
                                "bloodType" : "A"
                            }
                            """);
        }
    }

    @Nested
    @DisplayName("アイドルのキーワード検索")
    class KeywordSearchTest {
        @Test
        @DisplayName("キーワード「も」を指定した場合、該当するアイドルを取得できる")
        void keyword() {
            restTestClient.get().uri("/api/idols?keyword=も")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().json("""
                            [
                                {
                                    "id" : 1,
                                    "name" : "ふじの　もも",
                                    "birthday" : "2001-01-01",
                                    "bloodType" : "A"
                                }, {
                                    "id" : 3,
                                    "name" : "ささもと　みく",
                                    "birthday" : "2003-03-03",
                                    "bloodType" : "O"
                                }
                            ]
                            """);
        }
    }

    @Nested
    @DisplayName("アイドルの加入")
    class JoinTest {
        @Test
        @DisplayName("正しいアイドル情報をPOSTすると、アイドルを加入できる")
        void success() {
            restTestClient.post().uri("/api/idols")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {
                                "name" : "ひらい　りか",
                                "birthday" : "2006-06-06",
                                "bloodType" : "A"
                            }
                            """)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().location("/api/idols/6");
            Optional<Idol> idolOptional = idolRepository.selectById(6);
            assertEquals(new Idol(6, "ひらい　りか", LocalDate.of(2006, 6, 6), BloodType.A), idolOptional.get());
        }
    }

    @Nested
    @DisplayName("アイドル情報の修正")
    class FixTest {
        @Test
        @DisplayName("正しいアイドル情報をPUTすると、アイドル情報を修正できる")
        void success() {
            restTestClient.put().uri("/api/idols/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {
                                "name" : "ふじもと　ももか",
                                "birthday" : "2007-07-07",
                                "bloodType" : "B"
                            }
                            """)
                    .exchange()
                    .expectStatus().isOk();
            Optional<Idol> idolOptional = idolRepository.selectById(1);
            assertEquals(new Idol(1, "ふじもと　ももか", LocalDate.of(2007, 7, 7), BloodType.B), idolOptional.get());
        }
    }

    @Nested
    @DisplayName("アイドルの卒業")
    class GraduateTest {
        @Test
        @DisplayName("存在するIDを指定すると、アイドルが卒業する")
        void success() {
            restTestClient.delete().uri("/api/idols/1")
                    .exchange()
                    .expectStatus().isNoContent();
            int count = idolRepository.countById(1);
            assertEquals(0, count);
        }
    }
}