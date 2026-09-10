package com.springbootbook.ch10webapi02.webapi.request;

import com.springbootbook.ch10webapi02.persistence.entity.BloodType;
import com.springbootbook.ch10webapi02.persistence.entity.Idol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record IdolRequest(
        @NotBlank(message = "{IdolRequest.name.NotBlank}")
        @Length(min = 1, max = 32, message = "{IdolRequest.name.Length}")
        String name,
        @NotNull(message = "{IdolRequest.birthday.NotNull}")
        @Past(message = "{IdolRequest.birthday.Past}")
        LocalDate birthday,
        @NotNull(message = "{IdolRequest.bloodType.NotNull}")
        BloodType bloodType
) {
    public Idol toEntity() {
        return new Idol(null, name, birthday, bloodType);
    }

    public Idol toEntity(Integer id) {
        return new Idol(id, name, birthday, bloodType);
    }
}
