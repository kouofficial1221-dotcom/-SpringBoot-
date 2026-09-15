package com.springbootbook.ch09webapi.integration;

import com.springbootbook.ch09webapi.persistence.repository.IdolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;

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
                                },
                                {
                                    "id" : 3,
                                    "name" : "ささもと　みく",
                                    "birthday" : "2003-03-03",
                                    "bloodType" : "O"
                                }
                            ]
                            """);
        }
    }
}