package ua.arlabunakty.core.domain;

import java.util.Arrays;
import ua.arlabunakty.core.service.TimerDataConsumer;

/**
 * This class is thread-safe.
 */
public final class Timer {
    private final long start;
    private final Clock clock;
    private final TimerDataConsumer consumer;

    private final String category;
    private final String[] tags;

    /**
     * Constructs and starts a timer, which on stop sends recording time interval to the consumer.
     *
     * @param clock - the provider of current time.
     * @param consumer - the consumer of time interval for the given category and tags.
     * @param category - the category of the timer.
     * @param tags - the tags of the timer.
     */
    public Timer(Clock clock, TimerDataConsumer consumer, String category, String... tags) {
        this.start = clock.getTimeInMilliseconds();
        this.clock = clock;
        this.consumer = consumer;
        this.category = category;
        this.tags = Arrays.copyOf(tags, tags.length);
    }

    /**
     * Calculates the time interval between construction of the timer and the current time in milliseconds
     * and sends it to the consumer.
     */
    public void recordTimeInterval() {
        long operationTime = clock.getTimeInMilliseconds() - start;
        consumer.recordValue(operationTime, category, tags);
    }
}
