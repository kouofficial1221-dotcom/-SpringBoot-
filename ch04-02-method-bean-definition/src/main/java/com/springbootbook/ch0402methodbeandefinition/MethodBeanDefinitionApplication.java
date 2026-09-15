package com.springbootbook.ch0402methodbeandefinition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springbootbook.ch0402methodbeandefinition.pack1.Sample1;
import com.springbootbook.ch0402methodbeandefinition.pack2.Sample2;

@SpringBootApplication
public class MethodBeanDefinitionApplication {
    public static void main(String[] args) {
    		ApplicationContext context = SpringApplication.run(MethodBeanDefinitionApplication.class,args);
    		
    		Sample1 sample1 = context.getBean(Sample1.class);
    		sample1.method1();
    		
    		Sample2 sample2 = context.getBean(Sample2.class);
    		sample2.method2();
    }
}
