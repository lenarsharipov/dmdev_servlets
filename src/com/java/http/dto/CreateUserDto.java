package com.java.http.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    /*
        Все поля String так как все
        параметры из запроса приходят
        в виде пар ключ-значение,
        где оба являются строками.
        Например, в Spring идет
        автоматическое преобразование
        данных из String в искомый тип.

        Будем делать все преобразования
        вручную.
     */

    String name;
    String birthday;
    Part image;
    String email;
    String password;
    String role;
    String gender;

}
