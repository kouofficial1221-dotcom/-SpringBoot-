package com.springbootbook.ch0403di;

import com.springbootbook.ch0403di.pack1.Sample1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DiApplication {
    public static void main(String[] args) {
        // DIコンテナ作成＋コンポーネントスキャン（この時点でDIも実行される）
        ApplicationContext context = SpringApplication.run(DiApplication.class, args);
        // DIコンテナからSample1のBeanを取り出す
        Sample1 sample1 = context.getBean(Sample1.class);
        // Sample1のBeanを使う
        sample1.method1();
    }
}
