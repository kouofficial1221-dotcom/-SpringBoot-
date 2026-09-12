package com.springbootbook.ch0402methodbeandefinition;

import com.springbootbook.ch0402methodbeandefinition.pack1.Sample1;
import com.springbootbook.ch0402methodbeandefinition.pack2.Sample2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MethodBeanDefinitionApplication {
    public static void main(String[] args) {
        // DIコンテナ作成＋Bean生成
        ApplicationContext context = SpringApplication.run(MethodBeanDefinitionApplication.class, args);
        // DIコンテナからSample1のBeanを取り出す
        Sample1 sample1 = context.getBean(Sample1.class);
        // Sample1のBeanを使う
        sample1.method1();
        // DIコンテナからSample2のBeanを取り出す
        Sample2 sample2 = context.getBean(Sample2.class);
        // Sample2のBeanを使う
        sample2.method2();
    }
}
