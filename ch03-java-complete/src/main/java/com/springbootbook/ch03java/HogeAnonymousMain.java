package com.springbootbook.ch03java;

public class HogeAnonymousMain {
    public static void main(String[] args) {
        // これが無名クラス
        Hoge hoge = new Hoge() {
            @Override
            public int doSomething(String str) {
                return str.length();
            }
        };
        int length = hoge.doSomething("あいうえお");
        System.out.println(length);  // "5"と出力される
    }
}
