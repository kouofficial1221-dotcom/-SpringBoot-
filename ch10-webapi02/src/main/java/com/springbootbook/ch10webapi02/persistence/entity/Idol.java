package com.springbootbook.ch10webapi02.persistence.entity;

import java.time.LocalDate;

public record Idol(
        Integer id,
        String name,
        LocalDate birthday,
        BloodType bloodType) {
}
