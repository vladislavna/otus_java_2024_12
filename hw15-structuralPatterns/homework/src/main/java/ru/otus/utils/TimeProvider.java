package ru.otus.utils;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime getDate();

    int getCurrentSecond();
}
