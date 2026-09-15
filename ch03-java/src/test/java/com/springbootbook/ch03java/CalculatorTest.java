package com.springbootbook.ch03java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
	Calculator calculator;

	@BeforeEach
	void beforeEach() {
		calculator = new Calculator();
	}

	//自分の分けたい項目ごとに分けて実行することができる。
	@Nested
	@DisplayName("add()")
	class Addtest {
		//Javaの[main]メソッドみたいに、テスト対象のメソッドを定義できる。
		@Test
		//クラス・メソッドにつけてそれらを実行したときにどういう処理かなどをディスプレイに表示することができる。
		@DisplayName("1と2を指定すると3が返る")
		void success() {
			int actual = calculator.add(1, 2);
			//(期待値, 実際値)を引数に入れて、期待する値が得られているかを比較することができる。
			assertEquals(3, actual);
		}

		@Test
		@DisplayName("負の数を指定するとIllegalArgumentException")
		void negative() {
			//この処理を実行すると例外が起きるはずという便利メソッド
			assertThrows(IllegalArgumentException.class, () -> calculator.add(-1, -1));
		}
	}

	@Nested
	@DisplayName("subtract()")
	class SubtractTest {
		void success() {
			int actual = calculator.subtract(1, 2);
			assertEquals(-1, actual);
		}

	}

}
