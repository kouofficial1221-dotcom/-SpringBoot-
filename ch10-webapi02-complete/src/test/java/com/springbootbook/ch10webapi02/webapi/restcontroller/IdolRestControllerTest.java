package com.springbootbook.ch10webapi02.webapi.restcontroller;

import com.springbootbook.ch10webapi02.persistence.entity.BloodType;
import com.springbootbook.ch10webapi02.persistence.entity.Idol;
import com.springbootbook.ch10webapi02.service.IdolService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    @Nested
    @DisplayName("アイドルの加入")
    class JoinTest {
        @Test
        @DisplayName("正しいアイドル情報をPOSTすると、アイドルを加入できる")
        void success() {
            doReturn(new Idol(6, "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２", LocalDate.of(2006, 6, 6), BloodType.A)).when(idolService).join(any());
            // /api/idols にPOSTリクエスト
            restTestClient.post().uri("/api/idols")
                    // Content-Type: application/json
                    .contentType(MediaType.APPLICATION_JSON)
                    // リクエストボディ
                    .body("""
                            {
                                "name" : "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２",
                                "birthday" : "2006-06-06",
                                "bloodType" : "A"
                            }
                            """)
                    .exchange()
                    // ステータスコードが201 Createdになるはず
                    .expectStatus().isCreated()
                    // Locationヘッダーが/api/idols/6になるはず
                    .expectHeader().location("/api/idols/6");
        }

        @Test
        @DisplayName("空のアイドル情報をPOSTすると、400でエラーJSONが返る")
        void invalid01() {
            restTestClient.post().uri("/api/idols")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {
                                "name" : "",
                                "birthday" : null,
                                "bloodType" : null
                            }
                            """)
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody().json("""
                            {
                                "type" : "about:blank",
                                "title" : "Validation Error",
                                "status" : 400,
                                "detail" : "不正な入力値です",
                                "instance" : "/api/idols",
                                "invalidValues" : [
                                    {
                                        "field" : "name",
                                        "message" : "名前は必須です"
                                    },
                                    {
                                        "field" : "name",
                                        "message" : "名前は1文字以上32文字以下です"
                                    },
                                    {
                                        "field" : "birthday",
                                        "message" : "誕生日は必須です"
                                    },
                                    {
                                        "field" : "bloodType",
                                        "message" : "血液型は必須です"
                                    }
                                ]
                            }
                            """);
        }

        @Test
        @DisplayName("不正なアイドル情報をPOSTすると、400でエラーJSONが返る")
        void invalid02() {
            restTestClient.post().uri("/api/idols")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {
                                "name" : "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３",
                                "birthday" : "9999-12-31",
                                "bloodType" : "A"
                            }
                            """)
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody().json("""
                            {
                                "type" : "about:blank",
                                "title" : "Validation Error",
                                "status" : 400,
                                "detail" : "不正な入力値です",
                                "instance" : "/api/idols",
                                "invalidValues" : [
                                    {
                                        "field" : "name",
                                        "message" : "名前は1文字以上32文字以下です"
                                    },
                                    {
                                        "field" : "birthday",
                                        "message" : "誕生日は過去の日付にしてください"
                                    }
                                ]
                            }
                            """);
        }
    }

    @Nested
    @DisplayName("アイドル情報の修正")
    class FixTest {
        @Test
        @DisplayName("正しいアイドル情報をPUTすると、アイドル情報を修正できる")
        void success() {
            doReturn(true).when(idolService).exists(anyInt());
            doReturn(1).when(idolService).fix(any());
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
        }

        @Test
        @DisplayName("空のアイドル情報をPUTすると、400でエラーJSONが返る")
        void invalid01() {
            restTestClient.put().uri("/api/idols/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {
                                "name" : "",
                                "birthday" : null,
                                "bloodType" : null
                            }
                            """)
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody().json("""
                            {
                                "type" : "about:blank",
                                "title" : "Validation Error",
                                "status" : 400,
                                "detail" : "不正な入力値です",
                                "instance" : "/api/idols/1",
                                "invalidValues" : [
                                    {
                                        "field" : "name",
                                        "message" : "名前は必須です"
                                    },
                                    {
                                        "field" : "name",
                                        "message" : "名前は1文字以上32文字以下です"
                                    },
                                    {
                                        "field" : "birthday",
                                        "message" : "誕生日は必須です"
                                    },
                                    {
                                        "field" : "bloodType",
                                        "message" : "血液型は必須です"
                                    }
                                ]
                            }
                            """);
        }

        @Test
        @DisplayName("不正なアイドル情報をPUTすると、400でエラーJSONが返る")
        void invalid02() {
            restTestClient.put().uri("/api/idols/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {
                                "name" : "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３",
                                "birthday" : "9999-12-31",
                                "bloodType" : "A"
                            }
                            """)
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody().json("""
                            {
                                "type" : "about:blank",
                                "title" : "Validation Error",
                                "status" : 400,
                                "detail" : "不正な入力値です",
                                "instance" : "/api/idols/1",
                                "invalidValues" : [
                                    {
                                        "field" : "name",
                                        "message" : "名前は1文字以上32文字以下です"
                                    },
                                    {
                                        "field" : "birthday",
                                        "message" : "誕生日は過去の日付にしてください"
                                    }
                                ]
                            }
                            """);
        }

        @Test
        @DisplayName("存在しないIDを指定すると、404でエラーJSONが返る")
        void notFound() {
            doReturn(false).when(idolService).exists(anyInt());
            restTestClient.put().uri("/api/idols/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {
                                "name" : "ふじもと　ももか",
                                "birthday" : "2007-07-07",
                                "bloodType" : "B"
                            }
                            """)
                    .exchange()
                    .expectStatus().isNotFound()
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
    @DisplayName("アイドルの卒業")
    class GraduateTest {
        @Test
        @DisplayName("存在するIDを指定すると、アイドルが卒業する")
        void success() {
            doReturn(true).when(idolService).exists(anyInt());
            doReturn(1).when(idolService).graduate(anyInt());
            restTestClient.delete().uri("/api/idols/1")
                    .exchange()
                    .expectStatus().isNoContent();
        }

        @Test
        @DisplayName("存在しないIDを指定すると、404でエラーJSONが返る")
        void notFound() {
            doReturn(false).when(idolService).exists(anyInt());
            restTestClient.delete().uri("/api/idols/99")
                    .exchange()
                    .expectStatus().isNotFound()
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
}
