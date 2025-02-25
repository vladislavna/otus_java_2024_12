package ru.otus.l12.homework.exception;

import ru.otus.l12.homework.utils.ErrorsEnum;

public class MinCheckException extends RuntimeException {
    public MinCheckException(int requestedSum) {
        super(String.join(ErrorsEnum.ERR_MIN_CHECK.getMessage(), String.valueOf(requestedSum)));
    }
}
