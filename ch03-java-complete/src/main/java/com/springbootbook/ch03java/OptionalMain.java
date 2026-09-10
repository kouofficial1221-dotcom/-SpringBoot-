package com.springbootbook.ch03java;

import java.util.Optional;

public class OptionalMain {
    public static void main(String[] args) {
        IdolRepository idolRepository = new IdolRepository();
        // get()での取得
        try {
            // 検索結果がある場合
            Optional<Idol> idolOptional1 = idolRepository.findById(1);
            Idol idol1 = idolOptional1.get();
            System.out.println("アイドル情報が見つかりました: " + idol1);
            // 検索結果が無い場合
            Optional<Idol> idolOptional2 = idolRepository.findById(2);
            Idol idol2 = idolOptional2.get();
            System.out.println("アイドル情報が見つかりました: " + idol2);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        // orElseThrow()での取得
        try {
            // 検索結果がある場合
            Optional<Idol> idolOptional1 = idolRepository.findById(1);
            Idol idol1 = idolOptional1.orElseThrow(() -> new RuntimeException("該当するアイドル情報が存在しません。"));
            System.out.println("アイドル情報が見つかりました: " + idol1);
            // 検索結果が無い場合
            Optional<Idol> idolOptional2 = idolRepository.findById(2);
            Idol idol2 = idolOptional2.orElseThrow(() -> new RuntimeException("該当するアイドル情報が存在しません。"));
            System.out.println("アイドル情報が見つかりました: " + idol2);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        // map()で変換
        try {
            // 検索結果がある場合
            Optional<Idol> idolOptional1 = idolRepository.findById(1);
            Optional<String> nameOptional1 = idolOptional1.map(idol -> idol.name());
            String name1 = nameOptional1.orElseThrow(() -> new RuntimeException("該当するアイドル情報が存在しません。"));
            System.out.println("アイドル情報が見つかりました: " + name1);
            // 検索結果が無い場合
            Optional<Idol> idolOptional2 = idolRepository.findById(2);  // このOptionalは空
            Optional<String> nameOptional2 = idolOptional2.map(idol -> idol.name());  // このOptionalも空
            String name2 = nameOptional2.orElseThrow(() -> new RuntimeException("該当するアイドル情報が存在しません。"));
            System.out.println("アイドル情報が見つかりました: " + name2);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
