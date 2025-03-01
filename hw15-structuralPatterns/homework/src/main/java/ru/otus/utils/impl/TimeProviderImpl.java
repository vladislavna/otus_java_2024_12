package ru.otus.utils.impl;

import java.time.LocalDateTime;
import ru.otus.utils.TimeProvider;

public class TimeProviderImpl implements TimeProvider {
    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }

    @Override
    public int getCurrentSecond() {
        return getDate().getSecond();
    }
}
