package com.springbootbook.ch03java;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
	class ExecuteTest{
		
		@Test
		@DisplayName("キーワード「あ」をすると「件数は5件です。」が返る")
		void success() {
			//doReturn:モックにしているメソッドの値を固定する値
			//when:モック指定したクラスの指定
			//anyString:どんなString型でも受け付けるメソッド
			doReturn(5).when(sampleRepository).findByKeyword(anyString());
			String actual = sampleService.execute("あ");
			assertEquals("件数は5です。" , actual);
		}
		
		@Test
		@DisplayName("SampleRepositoryで例外が発生したらsampleExceptionがスローされる")
		void exception() {
			doThrow(new IllegalArgumentException()).when(sampleRepository).findByKeyword(anyString());
			//assertThrows：これは例外の発生に対する期待のメソッドで、例外が発生したらテストクリアのメソッド
			assertThrows(SampleException.class, () -> sampleService.execute("キーワード"));
		}
	}
}
