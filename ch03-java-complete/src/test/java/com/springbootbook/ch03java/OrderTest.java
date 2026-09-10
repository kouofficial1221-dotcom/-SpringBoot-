package com.springbootbook.ch03java;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll()");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll()");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach()");
    }

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
