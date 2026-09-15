package com.springbootbook.ch05database.persistence.entity;

import java.time.LocalDate;

public record Idol(
        Integer id,
        String name,
        LocalDate birthday,
        BloodType bloodType) {
}
