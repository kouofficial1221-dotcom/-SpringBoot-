package com.springbootbook.ch03java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void beforeEach() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("add()")
    class AddTest {
        @Test
        @DisplayName("1と2を指定すると3が返る")
        void success() {
            int actual = calculator.add(1, 2);
            assertEquals(3, actual);
        }

        @Test
        @DisplayName("負の数を指定するとIllegalArgumentException")
        void negative() {
            assertThrows(IllegalArgumentException.class, () -> calculator.add(-1, -1));
        }
    }

    @Nested
    @DisplayName("subtract()")
    class SubtractTest {
        @Test
        @DisplayName("1と2を指定すると-1が返る")
        void success() {
            int actual = calculator.subtract(1, 2);
            assertEquals(-1, actual);
        }
    }
}
