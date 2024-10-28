package ru.clevertec.servlet.dto;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.servlet.entity.enums.GenderEnum;


@Data
@Builder
public class ReadUserDto {
    private Integer id;
    private String name;
    private GenderEnum gender;
}
