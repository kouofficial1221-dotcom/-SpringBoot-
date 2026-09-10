package com.springbootbook.ch03java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StreamMain {
    public static void main(String[] args) {
        List<Idol> idolList = List.of(
                new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A),
                new Idol(2, "とうの　りんか", LocalDate.of(2002, 2, 2), BloodType.B),
                new Idol(3, "ささもと　みく", LocalDate.of(2003, 3, 3), BloodType.O),
                new Idol(4, "うえだ　ひなこ", LocalDate.of(2004, 4, 4), BloodType.AB),
                new Idol(5, "しょうじ　いおり", LocalDate.of(2005, 5, 5), BloodType.UNKNOWN)
        );

        // forとifで書く場合
        List<String> resultList1 = new ArrayList<>();
        for (Idol idol : idolList) {
            if (idol.birthday().isAfter(LocalDate.of(2002, 12, 31))) {
                resultList1.add(idol.name());
            }
        }
        System.out.println(resultList1);

        // Stream APIで書く場合
        // Streamを生成する
        List<String> resultList2 = idolList.stream()
                // 誕生日が2003年以降の要素のみ抽出する
                .filter(idol -> idol.birthday().isAfter(LocalDate.of(2002, 12, 31)))
                // IdolからString（名前）に変換する
                .map(idol -> idol.name())
                // Listに変換する
                .toList();
        System.out.println(resultList2);
    }
}
