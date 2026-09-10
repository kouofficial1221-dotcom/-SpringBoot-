package com.springbootbook.ch0401componentscan;

import com.springbootbook.ch0401componentscan.pack1.Sample1;
import com.springbootbook.ch0401componentscan.pack2.Sample2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ComponentScanApplication {
    public static void main(String[] args) {
        // DIコンテナ作成＋コンポーネントスキャン
        ApplicationContext context = SpringApplication.run(ComponentScanApplication.class, args);
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
