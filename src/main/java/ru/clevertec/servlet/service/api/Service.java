package ru.clevertec.servlet.service.api;

import ru.clevertec.servlet.dto.CreateUserDto;
import ru.clevertec.servlet.dto.ReadUserDto;
import ru.clevertec.servlet.entity.User;

import java.util.List;

public interface Service {

    User create(CreateUserDto dto);

    List<ReadUserDto> getAll();

    boolean deleteById(Integer id);

}
