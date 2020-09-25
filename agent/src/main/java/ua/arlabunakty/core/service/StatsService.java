package ua.arlabunakty.core.service;

import java.util.Collection;
import java.util.Objects;
import ua.arlabunakty.core.dao.HistoryDataDao;
import ua.arlabunakty.core.model.Clock;
import ua.arlabunakty.core.model.HistoryDataModel;
import ua.arlabunakty.core.model.TimerModel;

public class StatsService {

    private final HistoryDataDao historyDataDao;

    public StatsService(HistoryDataDao historyDataDao) {
        this.historyDataDao = historyDataDao;
    }

    public void recordValue(long value, String category, String... tags) {
        Objects.requireNonNull(category, "category should be non null");

        HistoryDataModel historyDataModel = new HistoryDataModel(value, category, tags);
        historyDataDao.append(historyDataModel);
    }

    public TimerModel registerTimer(String category, String... tags) {
        Objects.requireNonNull(category, "category should be non null");

        return new TimerModel(new Clock(), this::recordValue, category, tags);
    }

    public Collection<HistoryDataModel> findByTag(String tag) {
        Objects.requireNonNull(tag, "tag should be non null");

        return historyDataDao.findByTag(tag);
    }

    public Collection<HistoryDataModel> findByCategory(String category) {
        Objects.requireNonNull(category, "category should be non null");

        return historyDataDao.findByCategory(category);
    }
}
