package com.springbootbook.ch08validation.web.controller;

import com.springbootbook.ch08validation.persistence.entity.BloodType;
import com.springbootbook.ch08validation.persistence.entity.Idol;
import com.springbootbook.ch08validation.service.IdolService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IdolControllerTest {
    @MockitoBean
    IdolService idolService;

    @Autowired
    MockMvc mvc;

    @Nested
    @DisplayName("アイドル一覧画面")
    class IndexTest {
        @Test
        @DisplayName("アイドル一覧画面にアクセスすると200 OK")
        void success() throws Exception {
            mvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/index"));
        }
    }

    @Nested
    @DisplayName("キーワード検索")
    class KeywordSearchTest {
        @Test
        @DisplayName("キーワード「も」を指定すると200 OK")
        void success() throws Exception {
            mvc.perform(get("/idol").queryParam("keyword", "も"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/index"));
        }

        @Test
        @DisplayName("クエリパラメーターが無い場合も200 OK")
        void noParam() throws Exception {
            mvc.perform(get("/idol"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/index"));
        }
    }

    @Nested
    @DisplayName("新規加入画面")
    class JoinMainTest {
        @Test
        @DisplayName("新規加入画面にアクセスすると200 OK")
        void joinMain() throws Exception {
            mvc.perform(get("/idol/join"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/join"));
        }
    }

    @Nested
    @DisplayName("新規加入の実行")
    class JoinTest {
        @Test
        @DisplayName("正しい加入アイドルの情報を入力すると、アイドル一覧画面にリダイレクトする")
        void redirectToIndex() throws Exception {
            mvc.perform(post("/idol/join")
                    .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                    .param("birthday", "2006-06-06")
                    .param("bloodType", "A"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        }

        @Test
        @DisplayName("アイドル情報をすべて空で入力すると、新規加入画面に戻る")
        void empty() throws Exception {
            mvc.perform(post("/idol/join")
                            .param("name", "")
                            .param("birthday", "")
                            .param("bloodType", ""))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/join"));
        }

        @Test
        @DisplayName("33文字以上の名前を入力すると、新規加入画面に戻る")
        void tooLongName() throws Exception {
            mvc.perform(post("/idol/join")
                            .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３")
                            .param("birthday", "2006-06-06")
                            .param("bloodType", "UNKNOWN"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/join"));
        }
    }

    @Nested
    @DisplayName("修正画面")
    class FixMainTest {
        @Test
        @DisplayName("修正画面にアクセスすると200 OK")
        void fixMain() throws Exception {
            doReturn(Optional.of(new Idol(1, "ダミー", LocalDate.of(2001, 1, 1), BloodType.A))).when(idolService).findById(anyInt());
            mvc.perform(get("/idol/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/fix"));
        }

        @Test
        @DisplayName("存在しないIDを指定して修正画面にアクセスすると、エラー画面に遷移する")
        void doesNotExistMain() throws Exception {
            doReturn(Optional.empty()).when(idolService).findById(anyInt());
            mvc.perform(get("/idol/999"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"));
        }
    }

    @Nested
    @DisplayName("修正の実行")
    class FixTest {
        @Test
        @DisplayName("正しいアイドルの情報を入力すると、アイドル一覧画面にリダイレクトする")
        void redirectToIndex() throws Exception {
            doReturn(true).when(idolService).exists(anyInt());
            mvc.perform(post("/idol/1")
                            .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                            .param("birthday", "2006-06-06")
                            .param("bloodType", "A"))
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
                            .param("bloodType", ""))
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
                            .param("bloodType", "UNKNOWN"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("idol/fix"));
        }

        @Test
        @DisplayName("存在しないIDを指定して修正を行うと、エラー画面に遷移する")
        void doesNotExistFix() throws Exception {
            doReturn(false).when(idolService).exists(anyInt());
            mvc.perform(post("/idol/999")
                            .param("name", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２")
                            .param("birthday", "2006-06-06")
                            .param("bloodType", "A"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"));
        }
    }

    @Nested
    @DisplayName("アイドル卒業")
    class GraduateTest {
        @Test
        @DisplayName("卒業するアイドルを指定すると、アイドル一覧画面にリダイレクトする")
        void success() throws Exception {
            doReturn(true).when(idolService).exists(anyInt());
            mvc.perform(post("/idol/graduate/1"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        }

        @Test
        @DisplayName("存在しないIDを指定してアイドル卒業を行うと、エラー画面に遷移する")
        void doesNotExist() throws Exception {
            doReturn(false).when(idolService).exists(anyInt());
            mvc.perform(post("/idol/graduate/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"));
        }
    }
}
