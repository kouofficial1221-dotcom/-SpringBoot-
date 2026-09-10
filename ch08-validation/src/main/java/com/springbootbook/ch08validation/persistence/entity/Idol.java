package com.springbootbook.ch08validation.persistence.entity;

import java.time.LocalDate;

public record Idol(
        Integer id,
        String name,
        LocalDate birthday,
        BloodType bloodType) {
}
