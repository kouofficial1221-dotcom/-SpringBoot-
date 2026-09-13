package com.springbootbook.ch0403di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springbootbook.ch0403di.pack1.Sample1;

@SpringBootApplication
public class DiApplication {
    public static void main(String[] args) {
    		ApplicationContext context = SpringApplication.run(DiApplication.class, args);
    		Sample1 sample1 = context.getBean(Sample1.class);
    		sample1.method1();

    }
}
