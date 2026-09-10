package com.springbootbook.ch03java;

/**
 * 計算を行うクラスです。
 */
public class Calculator {
    /**
     * num1とnum2の和を返します。
     * @param num1 整数値
     * @param num2 整数値
     * @return num1とnum2の和
     * @throws IllegalArgumentException num1が負またはnum2が負の場合
     */
    public int add(int num1, int num2) {
        if (num1 < 0 || num2 < 0) {
            throw new IllegalArgumentException();
        }
        int result = num1 + num2;
        return result;
    }

    /**
     * num1とnum2の差を返します。
     * @param num1 整数値
     * @param num2 整数値
     * @return num1とnum2の差
     */
    public int subtract(int num1, int num2) {
        // わざと間違えています
        int result = num1 + num2;
        return result;
    }
}
