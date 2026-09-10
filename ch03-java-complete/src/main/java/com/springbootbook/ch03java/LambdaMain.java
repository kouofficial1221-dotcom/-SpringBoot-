package com.springbootbook.ch03java;

public class LambdaMain {
    public static void main(String[] args) {
        // これがラムダ式。やっていることは無名クラスの場合と全く同じです。
        Hoge hoge = (str) -> {
            return str.length();
        };
        int length = hoge.doSomething("あいうえお");
        System.out.println(length);  // "5"と出力される
    }
}
