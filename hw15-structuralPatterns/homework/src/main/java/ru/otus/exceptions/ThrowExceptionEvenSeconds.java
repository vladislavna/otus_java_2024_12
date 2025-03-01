package ru.otus.exceptions;

import ru.otus.utils.Constants;

public class ThrowExceptionEvenSeconds extends RuntimeException {
    public ThrowExceptionEvenSeconds() {
        super(Constants.EXCEPTION_EVEN_SECONDS_MESSAGE);
    }
}
