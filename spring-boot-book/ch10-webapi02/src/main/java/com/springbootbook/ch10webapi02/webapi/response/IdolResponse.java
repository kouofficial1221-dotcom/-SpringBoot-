package com.springbootbook.ch10webapi02.webapi.response;

import com.springbootbook.ch10webapi02.persistence.entity.BloodType;
import com.springbootbook.ch10webapi02.persistence.entity.Idol;

import java.time.LocalDate;

public record IdolResponse(
        Integer id,
        String name,
        LocalDate birthday,
        BloodType bloodType
) {
    public static IdolResponse fromEntity(Idol idol) {
        return new IdolResponse(idol.id(), idol.name(), idol.birthday(), idol.bloodType());
    }
}
