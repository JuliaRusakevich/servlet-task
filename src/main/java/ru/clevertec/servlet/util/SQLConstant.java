package ru.clevertec.servlet.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SQLConstant {

    public static final String SAVE_SQL = "INSERT INTO users (name, email, password, gender) VALUES (?, ?, ?, ?)";
    public static final String FIND_BY_ID = "select * from users where id =?";
    public static final String FIND_ALL = "select * from users";
    public static final String DELETE_BY_ID = "delete from users where id = ?";
}
