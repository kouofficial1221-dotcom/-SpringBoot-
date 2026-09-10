package com.springbootbook.ch0401componentscan.pack1;

import org.springframework.stereotype.Component;

@Component
public class Sample1 {
    public void method1() {
        System.out.println("method1()を実行します。");
    }
}
