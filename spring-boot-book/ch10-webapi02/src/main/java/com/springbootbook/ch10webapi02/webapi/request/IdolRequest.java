package com.springbootbook.ch10webapi02.webapi.request;

import com.springbootbook.ch10webapi02.persistence.entity.BloodType;
import com.springbootbook.ch10webapi02.persistence.entity.Idol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record IdolRequest(

) {

}
