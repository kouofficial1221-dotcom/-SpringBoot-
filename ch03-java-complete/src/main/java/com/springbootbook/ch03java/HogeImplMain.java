package com.springbootbook.ch03java;

public class HogeImplMain {
    public static void main(String[] args) {
        Hoge hoge = new HogeImpl();
        int length = hoge.doSomething("あいうえお");
        System.out.println(length);  // "5"と出力される
    }
}
