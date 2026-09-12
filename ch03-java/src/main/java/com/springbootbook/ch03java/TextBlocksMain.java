package com.springbootbook.ch03java;

public class TextBlocksMain {
	public static void main(String[] args) {
    	
    	String message1 = "おはよう\n" +
				"こんにちわ\n" +
				"こんばんわ\n";

	String message2 = """
			おはよう
			こんにちは
			こんばんは
			""";

	String sql = """
			SELECT
				id,
				name,
				birthday,
				blood_type
			FROM
				idol
			WHERE
				id = ?
			""";

	String json = """
			{
				"id": 1,
				"name": "ふじの もも",
				"birthday": "2001-01-01",
				"bloodType": "A",
			}
			""";
	}
 }