package ua.arlabunakty.core.model;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.arlabunakty.core.domain.Clock;
import ua.arlabunakty.core.domain.Timer;
import ua.arlabunakty.core.service.TimerDataConsumer;

@ExtendWith(MockitoExtension.class)
class TimerTest {

    @Mock
    private TimerDataConsumer consumer;

    @Mock
    private Clock clock;

    @Test
    void shouldRecordTimeInterval() {
        when(clock.getTimeInMilliseconds())
                .thenReturn(0L, 10000L);

        Timer timer = new Timer(clock, consumer, "testTimeInterval", "tag");

        timer.recordTimeInterval();

        verify(consumer)
                .recordValue(eq(10000L), eq("testTimeInterval"), eq("tag"));
    }
}

