package ru.otus.processor.homework;

import ru.otus.exceptions.ThrowExceptionEvenSeconds;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.utils.TimeProvider;

public class ProcessorThrowExceptionEvenSeconds implements Processor {

    private final TimeProvider timeProvider;

    public ProcessorThrowExceptionEvenSeconds(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        int currentSecond = timeProvider.getCurrentSecond();
        if (currentSecond % 2 == 0) {
            throw new ThrowExceptionEvenSeconds();
        }
        return message;
    }
}
