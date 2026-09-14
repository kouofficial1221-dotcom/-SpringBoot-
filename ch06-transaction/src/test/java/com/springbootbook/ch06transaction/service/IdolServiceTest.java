package com.springbootbook.ch06transaction.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.springbootbook.ch06transaction.persistence.entity.BloodType;
import com.springbootbook.ch06transaction.persistence.entity.Idol;
import com.springbootbook.ch06transaction.persistence.repository.IdolRepository;

public class IdolServiceTest {
	IdolService idolService;
	
	IdolRepository idolRepository;
	
	@BeforeEach
	void beforeEach() {
		idolRepository = mock(IdolRepository.class);
		idolService = new IdolService(idolRepository);
		
		@Nested
		@DisplayName("findById()")
		class FindByIdTest{
			@Test
			@DisplayName("存在するIDを指定すると、該当するアイドルを取得できる")
			void exists() {
				Optional<Idol> expected = Optional.of(new Idol(1,"ふじの　もも",LocalDate.of(2001, 1, 1),BloodType.A));
				doReturn(expected).when(idolRepository).selectById(anyInt());
				Optional<Idol> actual = idolService.	findById(1);
				assertEquals(expected,actual);
			}
			
			@Test
			@DisplayName("存在しないIDを指定すると、からのOptionalが返る")
			void doesnotExist() {
				Optional<Idol> expected = Optional.empty();
				doReturn(expected).when(idolRepository).selectById(anyInt());
				Optional<Idol>actual = idolService.findById(1);
				assertEquals(expected,actual);
			}
			
			@Test
			@DisplayName("存在しないIDを指定すると、空のOptionalが返る")
			void doesNotExist() {
				Optional<Idol> expected = Optional.empty();
				doReturn(expected).when(idolRepository).selectById(anyInt());
				Optional<Idol> actual = idolService.findById(1);
				assertEquals(expected, actual);
			}
			
		}
	}
}
