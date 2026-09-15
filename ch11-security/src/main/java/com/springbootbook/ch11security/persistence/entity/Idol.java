package com.springbootbook.ch11security.persistence.entity;

import java.time.LocalDate;

/**
 * アイドルを表すエンティティクラスです。
 * @param id ID
 * @param name 名前
 * @param birthday 誕生日
 * @param bloodType 血液型
 */
public record Idol(
        Integer id,
        String name,
        LocalDate birthday,
        BloodType bloodType) {
}
