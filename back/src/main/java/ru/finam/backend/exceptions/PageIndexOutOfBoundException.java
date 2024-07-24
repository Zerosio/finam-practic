package ru.finam.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PageIndexOutOfBoundException extends RuntimeException {

    public PageIndexOutOfBoundException(int pageNumber) {
        super("Данной страницы не существует, pageNumber: " + pageNumber);
    }
}
