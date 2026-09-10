package com.springbootbook.ch03java;

public class TextBlocksMain {
    public static void main(String[] args) {
        String message1 = "おはよう\n" +
                          "こんにちは\n" +
                          "こんばんは\n";
        System.out.println(message1);

        String message2 = """
                おはよう
                こんにちは
                こんばんは
                """;
        System.out.println(message2);
    }
}
