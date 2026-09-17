package com.springbootbook.ch07web.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.springbootbook.ch07web.service.IdolService;

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
    @DisplayName("アイドル卒業")
    class GraduateTest {
        @Test
        @DisplayName("卒業するアイドルを指定すると、アイドル一覧画面にリダイレクトする")
        void success() throws Exception {
            mvc.perform(post("/idol/graduate/1"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        }
    }
}
