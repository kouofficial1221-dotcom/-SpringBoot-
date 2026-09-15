package com.springbootbook.ch0402methodbeandefinition.config;

import com.springbootbook.ch0402methodbeandefinition.pack1.Sample1;
import com.springbootbook.ch0402methodbeandefinition.pack2.Sample2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Java Configクラスであることを示す
public class AppConfig {
    // このメソッドの戻り値がBeanになる
    @Bean
    public Sample1 sample1() {
        return new Sample1();
    }

    // このメソッドの戻り値がBeanになる
    @Bean
    public Sample2 sample2() {
        return new Sample2();
    }
}
