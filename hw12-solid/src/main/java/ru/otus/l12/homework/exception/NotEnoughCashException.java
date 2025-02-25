package ru.otus.l12.homework.exception;

import ru.otus.l12.homework.utils.ErrorsEnum;

public class NotEnoughCashException extends RuntimeException {
    public NotEnoughCashException(int requestedSum) {
        super(String.join(ErrorsEnum.ERR_NOT_ENOUGH.getMessage(), String.valueOf(requestedSum)));
    }
}
