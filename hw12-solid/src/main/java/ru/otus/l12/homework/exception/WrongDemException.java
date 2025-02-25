package ru.otus.l12.homework.exception;

import ru.otus.l12.homework.utils.ErrorsEnum;

public class WrongDemException extends RuntimeException {
    public WrongDemException(int requestedSum) {
        super(String.join(ErrorsEnum.ERR_WRONG_DEM.getMessage(), String.valueOf(requestedSum)));
    }
}
