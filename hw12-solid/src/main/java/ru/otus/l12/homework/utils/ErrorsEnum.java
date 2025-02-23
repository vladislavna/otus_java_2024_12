package ru.otus.l12.homework.utils;

public enum ErrorsEnum {
    ERR_NOT_ENOUGH("Not enough cash for operation"),
    ERR_WRONG_DEM("Cash cannot be issued"),
    ERR_MIN_CHECK("Needed sum is less than min banknote");

    private final String message;

    ErrorsEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
