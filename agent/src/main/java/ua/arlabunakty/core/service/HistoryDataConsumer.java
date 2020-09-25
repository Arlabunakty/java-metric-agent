package ua.arlabunakty.core.service;

@FunctionalInterface
public interface HistoryDataConsumer {
    void recordValue(long value, String category, String... tags);
}
