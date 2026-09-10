package com.springbootbook.ch08validation.web.form;

import com.springbootbook.ch08validation.persistence.entity.BloodType;
import com.springbootbook.ch08validation.persistence.entity.Idol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record IdolForm(

) {

}
