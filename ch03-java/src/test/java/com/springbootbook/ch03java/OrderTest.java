package com.springbootbook.ch03java;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderTest {
	//テストの数に限らず、1回だけ最初にテストをする
	@BeforeAll
	static void beforeAll() {
		System.out.println("beforeAll()");
	}
	
	//テストの数に限らず、1回だけ最後にテストをする
	@AfterAll
	static void afterAll() {
		System.out.println("afterAll()");
	}
	
	//テストの回数分、テストの前に実施する
	@BeforeEach
	void beforeEach() {
		System.out.println("beforeEach()");
	}
	
	//テストの回数分、テストの後に実施する
	@AfterEach
	void afterEach() {
		System.out.println("afterEach()");
	}
	
	@Test
	void test01() {
		System.out.println("test01()");
	}
	
	@Test
	void test02() {
		System.out.println("test02()");
	}
}
