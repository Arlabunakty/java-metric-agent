package ua.arlabunakty.core.service;

import java.util.Collection;
import java.util.Objects;
import ua.arlabunakty.core.dao.HistoryDataDao;
import ua.arlabunakty.core.model.Clock;
import ua.arlabunakty.core.model.HistoryDataModel;
import ua.arlabunakty.core.model.TimerModel;

public class StatsService {
    private final HistoryDataDao historyDataDao;

    /**
     * Constructs and initialize service to operate with history data source.
     *
     * @param historyDataDao - data source gor history data.
     * @throws NullPointerException if category is {@code null}.
     */
    public StatsService(HistoryDataDao historyDataDao) {
        Objects.requireNonNull(historyDataDao, "historyDataDao should be non null");
        this.historyDataDao = historyDataDao;
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

        HistoryDataModel historyDataModel = new HistoryDataModel(value, category, tags);
        historyDataDao.append(historyDataModel);
    }

    /**
     * Register timer, which may register value on demand.
     *
     * @param category - recorded timer interval will be attached to category.
     * @param tags - recorded timer interval will be tagged with.
     * @return timer.
     * @throws NullPointerException if category is {@code null}.
     */
    public TimerModel registerTimer(String category, String... tags) {
        Objects.requireNonNull(category, "category should be non null");

        return new TimerModel(new Clock(), this::recordValue, category, tags);
    }

    /**
     * Selects history data, which is tagged with given tag.
     *
     * @param tag - tag for search.
     * @return colelction of history data.
     * @throws NullPointerException if tag is {@code null}.
     */
    public Collection<HistoryDataModel> findByTag(String tag) {
        Objects.requireNonNull(tag, "tag should be non null");

        return historyDataDao.findByTag(tag);
    }

}
