package ru.otus.processor;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.exceptions.ThrowExceptionEvenSeconds;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorThrowExceptionEvenSeconds;
import ru.otus.utils.TimeProvider;

class ProcessorThrowExceptionEvenSecondsTest {

    @Test
    @DisplayName("ProcessorThrowExceptionEvenSeconds success")
    void When_OddSecond_Expect_Success() {
        TimeProvider mockTimeProvider = Mockito.mock(TimeProvider.class);
        when(mockTimeProvider.getCurrentSecond()).thenReturn(1);
        ProcessorThrowExceptionEvenSeconds processor = new ProcessorThrowExceptionEvenSeconds(mockTimeProvider);
        Message expectedMessage = new Message.Builder(1L).field1("1").build();
        Message actualMessage = processor.process(expectedMessage);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("ProcessorThrowExceptionEvenSeconds fail")
    void When_EvenSecond_Expect_Fail() {
        TimeProvider mockTimeProvider = Mockito.mock(TimeProvider.class);
        when(mockTimeProvider.getCurrentSecond()).thenReturn(2);
        ProcessorThrowExceptionEvenSeconds processor = new ProcessorThrowExceptionEvenSeconds(mockTimeProvider);
        Message message = new Message.Builder(1L).field1("1").build();
        Assertions.assertThrows(ThrowExceptionEvenSeconds.class, () -> processor.process(message));
    }
}
