package com.springbootbook.ch0403di.pack1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springbootbook.ch0403di.pack2.Sample2;

@Component
public class Sample1 {
	private final Sample2 sample2;
	
	@Autowired
	public Sample1(Sample2 sample2) {
		this.sample2 = sample2;
	}
	
	public void method1() {
		System.out.println("method1()を実行します。");
		sample2.method2();
	}
	

}
