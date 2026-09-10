package com.springbootbook.ch11security.web.controller;

import com.springbootbook.ch11security.persistence.entity.BloodType;
import com.springbootbook.ch11security.persistence.entity.Idol;
import com.springbootbook.ch11security.service.IdolService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IdolControllerTest {
    private static final String MANAGER = "manager@idol.com";

    private static final String OPERATOR = "operator@idol.com";

    @MockitoBean
    IdolService idolService;

    @Autowired
    MockMvc mvc;

    @Nested
    @DisplayName("ログイン画面")
    class LoginMainTest {
        @Test
        @DisplayName("未ログインでアクセスすると200 OK")
        @WithAnonymousUser
        void success() throws Exception {
            mvc.perform(get("/login"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("login"));
        }
    }

    @Nested
    @DisplayName("ログイン実行")
    class LoginTest {
        @Nested
        @DisplayName("管理者権限でのログイン")
        @WithAnonymousUser
        class Manager {
            @Test
            @DisplayName("ログインするとアイドル一覧画面にリダイレクトされる")
            void success() throws Exception {
                mvc.perform(post("/login")
                                .param("username", "manager@idol.com")
                                .param("password", "manager")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }
        }

        @Nested
        @DisplayName("不正なユーザー名・パスワード")
        @WithAnonymousUser
        class Failed {
            @Test
            @DisplayName("ログインするとログイン画面にリダイレクトされる")
            void failed() throws Exception {
                mvc.perform(post("/login")
                                .param("username", "xxx")
                                .param("password", "xxx")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login?error"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限でのログイン")
        @WithAnonymousUser
        class Operator {
            @Test
            @DisplayName("ログインするとアイドル一覧画面にリダイレクトされる")
            void success() throws Exception {
                mvc.perform(post("/login")
                                .param("username", "operator@idol.com")
                                .param("password", "operator")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }
        }
    }

    @Nested
    @DisplayName("アイドル一覧画面")
    class IndexTest {
        @Nested
        @DisplayName("管理者権限")
        @WithUserDetails(MANAGER)
        class Manager {
            @Test
            @DisplayName("アクセスすると200 OK")
            void success() throws Exception {
                mvc.perform(get("/"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/index"));
            }
        }

        @Nested
        @DisplayName("未ログイン")
        @WithAnonymousUser
        class Anonymous {
            @Test
            @DisplayName("アクセスするとログイン画面にリダイレクト")
            void redirect() throws Exception {
                mvc.perform(get("/"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限")
        @WithUserDetails(OPERATOR)
        class Operator {
            @Test
            @DisplayName("アクセスすると200 OK")
            void success() throws Exception {
                mvc.perform(get("/"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/index"));
            }
        }
    }

    @Nested
    @DisplayName("キーワード検索")
    class KeywordSearchTest {
        @Nested
        @DisplayName("管理者権限")
        @WithUserDetails(MANAGER)
        class Manager {
            @Test
            @DisplayName("キーワード「も」を指定すると200 OK")
            void success() throws Exception {
                mvc.perform(get("/idol").queryParam("keyword", "も"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/index"));
            }

            @Test
            @DisplayName("クエリパラメーターが無い場合は200 OK")
            void noParam() throws Exception {
                mvc.perform(get("/idol"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/index"));
            }
        }

        @Nested
        @DisplayName("未ログイン")
        @WithAnonymousUser
        class Anonymous {
            @Test
            @DisplayName("キーワード検索するとログイン画面にリダイレクト")
            void redirect() throws Exception {
                mvc.perform(get("/idol"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限")
        @WithUserDetails(OPERATOR)
        class Operator {
            @Test
            @DisplayName("クエリパラメーターがない場合は200 OK")
            void noParam() throws Exception {
                mvc.perform(get("/idol"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/index"));
            }
        }
    }

    @Nested
    @DisplayName("新規加入画面")
    class JoinMainTest {
        @Nested
        @DisplayName("管理者権限")
        @WithUserDetails(MANAGER)
        class Manager {
            @Test
            @DisplayName("アクセスすると200 OK")
            void success() throws Exception {
                mvc.perform(get("/idol/join"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/join"));
            }
        }

        @Nested
        @DisplayName("未ログイン")
        @WithAnonymousUser
        class Anonymous {
            @Test
            @DisplayName("アクセスするとログイン画面にリダイレクト")
            void redirect() throws Exception {
                mvc.perform(get("/idol/join"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限")
        @WithUserDetails(OPERATOR)
        class Operator {
            @Test
            @DisplayName("アクセスすると200 OK")
            void success() throws Exception {
                mvc.perform(get("/idol/join"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/join"));
            }
        }
    }

    @Nested
    @DisplayName("新規加入の実行")
    class JoinTest {
        @Nested
        @DisplayName("管理者権限")
        @WithUserDetails(MANAGER)
        class Manager {
            @Test
            @DisplayName("正しい加入アイドルの情報を入力すると、アイドル一覧画面にリダイレクトする")
            void success() throws Exception {
                mvc.perform(post("/idol/join")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "A")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }

            @Test
            @DisplayName("アイドル情報をすべて空で入力すると、新規加入画面に戻る")
            void empty() throws Exception {
                mvc.perform(post("/idol/join")
                                .param("name", "")
                                .param("birthday", "")
                                .param("bloodType", "")
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/join"));
            }

            @Test
            @DisplayName("33文字以上の名前を入力すると、新規加入画面に戻る")
            void tooLongName() throws Exception {
                mvc.perform(post("/idol/join")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "UNKNOWN")
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/join"));
            }
        }

        @Nested
        @DisplayName("未ログイン")
        @WithAnonymousUser
        class Anonymous {
            @Test
            @DisplayName("正しい加入アイドルの情報を入力するとログイン画面にリダイレクト")
            void redirect() throws Exception {
                mvc.perform(post("/idol/join")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "A")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限")
        class Operator {
            @Test
            @DisplayName("正しい加入アイドルの情報を入力すると、アイドル一覧画面にリダイレクトする")
            @WithUserDetails(OPERATOR)
            void success() throws Exception {
                mvc.perform(post("/idol/join")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "A")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }
        }
    }

    @Nested
    @DisplayName("修正画面")
    class FixMainTest {
        @Nested
        @DisplayName("管理者権限")
        @WithUserDetails(MANAGER)
        class Manager {
            @Test
            @DisplayName("アクセスすると200 OK")
            void success() throws Exception {
                doReturn(Optional.of(new Idol(1, "ダミー", LocalDate.of(2001, 1, 1), BloodType.A))).when(idolService).findById(anyInt());
                mvc.perform(get("/idol/1"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/fix"));
            }

            @Test
            @DisplayName("存在しないIDを指定してアクセスすると、エラー画面に遷移する")
            void doesNotExist() throws Exception {
                doReturn(Optional.empty()).when(idolService).findById(anyInt());
                mvc.perform(get("/idol/999"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("error"));
            }
        }

        @Nested
        @DisplayName("未ログイン")
        @WithAnonymousUser
        class Anonymous {
            @Test
            @DisplayName("修正画面にアクセスするとログイン画面にリダイレクト")
            void redirect() throws Exception {
                doReturn(Optional.of(new Idol(1, "ダミー", LocalDate.of(2001, 1, 1), BloodType.A))).when(idolService).findById(anyInt());
                mvc.perform(get("/idol/1"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限")
        @WithUserDetails(OPERATOR)
        class Operator {
            @Test
            @DisplayName("アクセスすると200 OK")
            void success() throws Exception {
                doReturn(Optional.of(new Idol(1, "ダミー", LocalDate.of(2001, 1, 1), BloodType.A))).when(idolService).findById(anyInt());
                mvc.perform(get("/idol/1"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/fix"));
            }
        }
    }

    @Nested
    @DisplayName("修正の実行")
    class FixTest {
        @Nested
        @DisplayName("管理者権限")
        @WithUserDetails(MANAGER)
        class Manager {
            @Test
            @DisplayName("正しいアイドルの情報を入力すると、アイドル一覧画面にリダイレクトする")
            void success() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/1")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "A")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }

            @Test
            @DisplayName("アイドル情報をすべて空で入力すると、修正画面に戻る")
            void empty() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/1")
                                .param("name", "")
                                .param("birthday", "")
                                .param("bloodType", "")
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/fix"));
            }

            @Test
            @DisplayName("33文字以上の名前を入力すると、修正画面に戻る")
            void tooLongName() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/1")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "UNKNOWN")
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name("idol/fix"));
            }

            @Test
            @DisplayName("存在しないIDを指定して修正を行うと、エラー画面に遷移する")
            void doesNotExist() throws Exception {
                doReturn(false).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/999")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "A")
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name("error"));
            }
        }

        @Nested
        @DisplayName("未ログイン")
        class Anonymous {
            @Test
            @DisplayName("正しいアイドルの情報を入力すると、ログイン画面にリダイレクトする")
            void redirect() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/1")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "A")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限")
        @WithUserDetails(OPERATOR)
        class Operator {
            @Test
            @DisplayName("正しいアイドルの情報を入力すると、アイドル一覧画面にリダイレクトする")
            void success() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/1")
                                .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                                .param("birthday", "2006-06-06")
                                .param("bloodType", "A")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }
        }
    }

    @Nested
    @DisplayName("アイドル卒業")
    class GraduateTest {
        @Nested
        @DisplayName("管理者権限")
        @WithUserDetails(MANAGER)
        class Manager {
            @Test
            @DisplayName("卒業するアイドルを指定すると、アイドル一覧画面にリダイレクトする")
            void success() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/graduate/1")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }

            @Test
            @DisplayName("存在しないIDを指定してアイドル卒業を行うと、エラー画面に遷移する")
            void doesNotExist() throws Exception {
                doReturn(false).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/graduate/99")
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name("error"));
            }
        }

        @Nested
        @DisplayName("未ログイン")
        @WithAnonymousUser
        class Anonymous {
            @Test
            @DisplayName("卒業するアイドルを指定すると、ログイン画面にリダイレクトする")
            void redirect() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/graduate/1")
                                .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/login"));
            }
        }

        @Nested
        @DisplayName("オペレーター権限")
        @WithUserDetails(OPERATOR)
        class Operator {
            @Test
            @DisplayName("卒業するアイドルを指定すると、403 Forbidden")
            void forbidden() throws Exception {
                doReturn(true).when(idolService).exists(anyInt());
                mvc.perform(post("/idol/graduate/1")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
                // error/403.htmlに遷移するかはMockMvcでテストできないので、ステータスコードのみテストする
            }
        }
    }
}
