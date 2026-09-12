package com.springbootbook.ch11security.web.form;

import com.springbootbook.ch11security.persistence.entity.BloodType;
import com.springbootbook.ch11security.persistence.entity.Idol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * アイドルの新規加入や修正に利用するフォームクラスです。
 * @param name 名前
 * @param birthday 誕生日
 * @param bloodType 血液型
 */
public record IdolForm(
        @NotBlank @Length(min = 1, max = 32) String name,
        @NotNull @Past LocalDate birthday,
        @NotNull BloodType bloodType) {
    /**
     * 全コンポーネントがnullなIdolFormインスタンスを生成します。
     */
    public static IdolForm empty() {
        return new IdolForm(null, null, null);
    }

    /**
     * IdolインスタンスからIdolFormインスタンスを生成します。
     * @param idol Idolインスタンス
     */
    public static IdolForm fromEntity(Idol idol) {
        return new IdolForm(idol.name(), idol.birthday(), idol.bloodType());
    }

    /**
     * IdolFormインスタンスからID値を持たないIdolインスタンス（＝idはnull）を生成します。
     */
    public Idol toEntity() {
        return new Idol(null, name, birthday, bloodType);
    }

    /**
     * IdolFormインスタンスからID値を持つIdolインスタンスを生成します。
     * @param id アイドルID
     */
    public Idol toEntity(Integer id) {
        return new Idol(id, name, birthday, bloodType);
    }
}
