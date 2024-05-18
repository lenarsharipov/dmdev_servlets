package com.java.http.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult {
    /*
        Можно поставить параметром String,
        но тогда у нас не будет возможности
        интернационализации ошибок.
        Лучше создать объект Error, в котором
        будут 2 поля - код и сама ошибка.
     */
    private final List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

}