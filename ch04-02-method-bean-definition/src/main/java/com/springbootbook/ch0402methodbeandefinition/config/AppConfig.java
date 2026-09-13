package com.springbootbook.ch0402methodbeandefinition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbootbook.ch0402methodbeandefinition.pack1.Sample1;
import com.springbootbook.ch0402methodbeandefinition.pack2.Sample2;

@Configuration
public class AppConfig {
	@Bean
	public Sample1 sample1() {
		return new Sample1();
	}
	
	@Bean
	public Sample2 sample2() {
		return new Sample2();
	}
}
