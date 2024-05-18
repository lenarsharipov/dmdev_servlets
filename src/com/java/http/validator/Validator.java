package com.java.http.validator;

public interface Validator<T> {
    /*
        Есть 2 варианта валидации:
         1 - проброс exception
         2 - объект с ValidationResult
     */
    ValidationResult isValid(T object);

}
