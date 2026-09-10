package com.springbootbook.ch09webapi.webapi.restcontroller;

import com.springbootbook.ch09webapi.persistence.entity.BloodType;
import com.springbootbook.ch09webapi.persistence.entity.Idol;
import com.springbootbook.ch09webapi.service.IdolService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureRestTestClient
public class IdolRestControllerTest {
    @MockitoBean
    IdolService idolService;

    @Autowired
    RestTestClient restTestClient;

    @Nested
    @DisplayName("アイドルのID検索")
    class IdSearchTest {
        @Test
        @DisplayName("存在するIDを指定すると、該当するアイドルを取得できる")
        void success() {
            // idolService.findById(1)を実行したら、「ふじの　もも」の情報が返ってくるようモックする
            doReturn(Optional.of(new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A)))
                    .when(idolService).findById(anyInt());
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

        @Test
        @DisplayName("存在しないIDを指定すると、404でエラーJSONが返る")
        void notFound() {
            // idolService.findById(99)を実行したら、空のOptionalが返ってくるようモックする
            doReturn(Optional.empty()).when(idolService).findById(anyInt());
            // /api/idols/99 にGETリクエストしたら
            restTestClient.get().uri("/api/idols/99")
                    .exchange()
                    // ステータスコードが404 Not Foundになるはず
                    .expectStatus().isNotFound()
                    // レスポンスボディが次のようなProblem DetailsのJSONになるはず
                    .expectBody().json("""
                            {
                                "type" : "about:blank",
                                "title" : "Not Found",
                                "status" : 404,
                                "detail" : "該当するアイドルが存在しません",
                                "instance" : "/api/idols/99",
                                "idolId" : 99
                            }
                            """);
        }
    }

    @Nested
    @DisplayName("アイドルのキーワード検索")
    class KeywordSearchTest {
        @Test
        @DisplayName("キーワードを指定しない場合、全アイドルを取得できる")
        void all() {
            doReturn(List.of(
                    new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A),
                    new Idol(2, "とうの　りんか", LocalDate.of(2002, 2, 2), BloodType.B)
            )).when(idolService).findByNameOrderById(anyString());
            restTestClient.get().uri("/api/idols")
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
                                    "id" : 2,
                                    "name" : "とうの　りんか",
                                    "birthday" : "2002-02-02",
                                    "bloodType" : "B"
                                }
                            ]
                            """);
        }

        @Test
        @DisplayName("キーワード「も」を指定した場合、該当するアイドルを取得できる")
        void keyword() {
            doReturn(List.of(
                    new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A),
                    new Idol(3, "ささもと　みく", LocalDate.of(2003, 3, 3), BloodType.O)
            )).when(idolService).findByNameOrderById(anyString());
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

        @Test
        @DisplayName("キーワード「を」を指定した場合、空のJSON配列を取得できる")
        void empty() {
            doReturn(List.of()).when(idolService).findByNameOrderById(anyString());
            restTestClient.get().uri("/api/idols?keyword=を")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().json("[]");
        }
    }
}
