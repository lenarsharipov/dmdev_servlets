package com.java.http.service;

import com.java.http.dao.UserDao;
import com.java.http.dto.CreateUserDto;
import com.java.http.dto.UserDto;
import com.java.http.entity.User;
import com.java.http.exception.ValidationException;
import com.java.http.mapper.CreateUserMapper;
import com.java.http.mapper.UserMapper;
import com.java.http.validator.CreateUserValidator;
import com.java.http.validator.ValidationResult;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    public Optional<UserDto> login(String email, String password) {

        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);

    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public Integer create(CreateUserDto userDto) {
        /*
            Нам нужен валидатор. Валидацию можно
            проводить на уровне сервисов. Но также
            можно на уровне сервлетов. Но это не удобно,
            так как на уровне сервисов мы можем сходить
            в БД и проверить есть ли уже email, который
            был передан в сервлет. В Спринге есть декларативный
            вариант, который используется не на уровне сервиса.
            Но валидация на уровне сервиса - хороший вариант.
         */
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User userEntity = createUserMapper.mapFrom(userDto);
        imageService.upload(
                userEntity.getImage(),
                userDto.getImage().getInputStream()
        );
        userDao.save(userEntity);
        return userEntity.getId();
    }
}
