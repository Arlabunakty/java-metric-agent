package ua.arlabunakty.core.service;

@FunctionalInterface
public interface TimerDataConsumer {
    void recordValue(long value, String category, String... tags);
}
