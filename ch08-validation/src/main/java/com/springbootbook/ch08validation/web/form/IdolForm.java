package com.springbootbook.ch08validation.web.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.springbootbook.ch08validation.persistence.entity.BloodType;
import com.springbootbook.ch08validation.persistence.entity.Idol;

public record IdolForm(
		@NotBlank @Length(min = 1, max = 32) String name,
		LocalDate birthday,
		BloodType bloodType) {
	
	public static IdolForm empty() {
		return new IdolForm(null, null, null);
	}
	
	public static IdolForm fromEntity(Idol idol) {
		return new IdolForm(idol.name(),idol.birthday(),idol.bloodType());
	}
	
	public Idol toEntity() {
		return new Idol(null, name, birthday, bloodType);
	}
	
	public Idol toEntity(Integer id) {
		return new Idol(id, name, birthday, bloodType);
	}

}
