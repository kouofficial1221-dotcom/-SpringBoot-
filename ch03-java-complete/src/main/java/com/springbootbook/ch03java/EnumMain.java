package com.springbootbook.ch03java;

public class EnumMain {
    public static void main(String[] args) {
        BloodType bloodType = BloodType.A;
        String bloodTypeName = bloodType.name();  // "A"が返る
        System.out.println(bloodTypeName);
    }
}
