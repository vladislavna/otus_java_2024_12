package ru.otus.l12.homework.exception;

public class NotEnoughCashException extends RuntimeException {
    public NotEnoughCashException(String message) {
        super(message);
    }
}
