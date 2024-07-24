package ru.finam.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalPageLimitException extends RuntimeException{
    public IllegalPageLimitException(int limit) {
        super("Неправильное значение числа элементов страницы : " + limit);
    }
}
