package ru.otus.exception;

public class DataTemplateJdbcException extends RuntimeException {
    public DataTemplateJdbcException(Exception message) {
        super(message);
    }
}
