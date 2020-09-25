package ua.arlabunakty.core.model;

import java.util.Arrays;
import ua.arlabunakty.core.service.HistoryDataConsumer;

public final class TimerModel {
    private final long start;
    private final Clock clock;
    private final HistoryDataConsumer consumer;

    private final String category;
    private final String[] tags;

    public TimerModel(Clock clock, HistoryDataConsumer consumer, String category, String... tags) {
        this.start = clock.getTimeInMilliseconds();
        this.clock = clock;
        this.consumer = consumer;
        this.category = category;
        if (tags == null) {
            this.tags = new String[0];
        } else {
            this.tags = Arrays.copyOf(tags, tags.length);
        }
    }

    public void stopAndRecord() {
        long operationTime = clock.getTimeInMilliseconds() - start;
        consumer.recordValue(operationTime, category, tags);
    }
}
