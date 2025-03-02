package ru.otus.utils;

import ch.qos.logback.core.joran.spi.ActionException;

public class Constants {
    private Constants() throws ActionException {
        throw new ActionException("Not for create");
    }

    public static final String EXCEPTION_EVEN_SECONDS_MESSAGE = "Test Exception";
}
