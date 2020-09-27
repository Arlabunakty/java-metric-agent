package ua.arlabunakty.core.service;

import java.util.Collection;
import java.util.Objects;
import ua.arlabunakty.core.dao.HistoryDataDao;
import ua.arlabunakty.core.model.HistoryDataModel;
import ua.arlabunakty.core.model.MetricModel;

public class MetricService {
    private final HistoryDataDao historyDataDao;

    public MetricService(HistoryDataDao historyDataDao) {
        Objects.requireNonNull(historyDataDao, "historyDataDao should be non null");
        this.historyDataDao = historyDataDao;
    }

    /**
     * Select history data and builds metric model based on aggregated values:
     * - minimum value;
     * - maximum value;
     * - average value.
     *
     * @param category - category to select history data.
     * @return aggregated metric model or null if no history data exists.
     */
    public MetricModel getMetricByCategory(String category) {
        Objects.requireNonNull(category, "category should be non null");

        final Collection<HistoryDataModel> historyData = historyDataDao.findByCategory(category);
        if (historyData.isEmpty()) {
            return null;
        }

        double min = Double.MAX_VALUE;
        double sum = 0;
        double max = Double.MIN_VALUE;
        for (HistoryDataModel historyDatum : historyData) {
            double value = historyDatum.getValue();
            min = Math.min(min, value);
            sum += value;
            max = Math.max(max, value);
        }

        return new MetricModel(category, min, max, sum / historyData.size());
    }
}
