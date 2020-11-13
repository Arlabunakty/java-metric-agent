package ua.arlabunakty.core.service;

import java.util.Collection;
import java.util.Objects;
import ua.arlabunakty.core.dao.HistoryDao;
import ua.arlabunakty.core.domain.Clock;
import ua.arlabunakty.core.domain.HistoryRecord;
import ua.arlabunakty.core.domain.Timer;

public class StatsService {
    private final HistoryDao historyDao;

    /**
     * Constructs and initialize service to operate with history data source.
     *
     * @param historyDao - data source gor history data.
     * @throws NullPointerException if category is {@code null}.
     */
    public StatsService(HistoryDao historyDao) {
        Objects.requireNonNull(historyDao, "historyDataDao should be non null");
        this.historyDao = historyDao;
    }

    /**
     * Records value as history data with given category and tags.
     *
     * @param value - given value.
     * @param category - attached category.
     * @param tags - tagged with.
     * @throws NullPointerException if category is {@code null}.
     */
    public void recordValue(long value, String category, String... tags) {
        Objects.requireNonNull(category, "category should be non null");

        HistoryRecord historyRecord = new HistoryRecord(value, category, tags);
        historyDao.append(historyRecord);
    }

    /**
     * Register timer, which may register value on demand.
     *
     * @param category - recorded timer interval will be attached to category.
     * @param tags - recorded timer interval will be tagged with.
     * @return timer.
     * @throws NullPointerException if category is {@code null}.
     */
    public Timer registerTimer(String category, String... tags) {
        Objects.requireNonNull(category, "category should be non null");

        return new Timer(new Clock(), this::recordValue, category, tags);
    }

    /**
     * Selects history data, which is tagged with given tag.
     *
     * @param tag - tag for search.
     * @return colelction of history data.
     * @throws NullPointerException if tag is {@code null}.
     */
    public Collection<HistoryRecord> findByTag(String tag) {
        Objects.requireNonNull(tag, "tag should be non null");

        return historyDao.findByTag(tag);
    }

    public Collection<HistoryRecord> findByCategory(String category) {
        Objects.requireNonNull(category, "category should be non null");

        return historyDao.findByCategory(category);
    }
}
