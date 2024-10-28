package ru.clevertec.servlet.service;


import ru.clevertec.servlet.dto.CreateUserDto;
import ru.clevertec.servlet.dto.ReadUserDto;
import ru.clevertec.servlet.entity.User;
import ru.clevertec.servlet.mapper.UserMapper;
import ru.clevertec.servlet.repository.UserRepository;
import ru.clevertec.servlet.service.api.Service;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Service {

    private final static UserService INSTANCE = new UserService();
    private final UserRepository userRepository = UserRepository.getInstance();
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public User create(CreateUserDto dto) {
        User user = userMapper.toUser(dto);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (findById(id)) {
            userRepository.delete(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ReadUserDto> getAll() {
        List<ReadUserDto> users = new ArrayList<>();
        List<User> usersFromDB = userRepository.findAll();
        for (User user : usersFromDB) {
            users.add(userMapper.toReadUserDto(user));
        }
        return users;

    }

    private boolean findById(Integer id) {
        User userFromDB = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User was not found"));
        return userFromDB.getId() > 0;
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
