package com.springbootbook.ch03java;

import java.time.LocalDate;
import java.util.Optional;

/**
 * アイドルデータベースにアクセスするクラスです。
 */
public class IdolRepository {
    /**
     * アイドルIDに該当するアイドル情報を検索して返します。
     * @param id アイドルID
     * @return 検索結果があった場合はアイドルを保持するOptional、無い場合は空のOptional
     */
    public Optional<Idol> findById(Integer id) {
        // 簡略化のため、ID = 1ならば検索結果あり、それ以外は無し
        if (id == 1) {
            Idol idol = new Idol(1, "ふじの　もも", LocalDate.of(2001, 1, 1), BloodType.A);
            // アイドルを保持するOptionalを生成して返す
            Optional<Idol> idolOptional = Optional.of(idol);
            return idolOptional;
        }
        // 空のOptionalを返す
        return Optional.empty();
    }
}
