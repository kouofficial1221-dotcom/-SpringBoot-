package com.springbootbook.ch03java;

class HogeImpl implements Hoge {
    @Override
    public int doSomething(String str) {
        return str.length();
    }
}
