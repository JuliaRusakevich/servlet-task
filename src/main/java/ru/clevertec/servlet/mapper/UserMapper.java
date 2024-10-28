package ru.clevertec.servlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.clevertec.servlet.dto.CreateUserDto;
import ru.clevertec.servlet.dto.ReadUserDto;
import ru.clevertec.servlet.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(CreateUserDto dto);

    ReadUserDto toReadUserDto(User user);

}
