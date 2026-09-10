package com.springbootbook.ch03java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SampleServiceTest {

    SampleService sampleService;

    SampleRepository sampleRepository;

    @BeforeEach
    void beforeEach() {
        sampleRepository = mock(SampleRepository.class);
        sampleService = new SampleService(sampleRepository);
    }

    @Nested
    @DisplayName("execute()")
    class ExecuteTest {
        @Test
        @DisplayName("キーワード「あ」を指定すると「件数は5です。」が返る")
        void success() {
            doReturn(5).when(sampleRepository).findByKeyword(anyString());
            String actual = sampleService.execute("あ");
            assertEquals("件数は5です。", actual);
        }

        @Test
        @DisplayName("SampleRepositoryで例外が発生したらSampleExceptionがスローされる")
        void exception() {
            doThrow(new IllegalArgumentException()).when(sampleRepository).findByKeyword(anyString());
            assertThrows(SampleException.class, () -> sampleService.execute("キーワード"));
        }
    }
}
