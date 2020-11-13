package ua.arlabunakty.core.service;

import java.util.Collection;
import java.util.Objects;
import ua.arlabunakty.core.domain.HistoryRecord;
import ua.arlabunakty.core.domain.Metric;

public class MetricService {
    private final StatsService statsService;

    public MetricService(StatsService statsService) {
        Objects.requireNonNull(statsService, "statsService should be non null");
        this.statsService = statsService;
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
    public Metric getMetricByCategory(String category) {
        Objects.requireNonNull(category, "category should be non null");

        final Collection<HistoryRecord> historyRecordData = statsService.findByCategory(category);
        if (historyRecordData.isEmpty()) {
            return null;
        }

        double min = Double.MAX_VALUE;
        double sum = 0;
        double max = Double.MIN_VALUE;
        for (HistoryRecord historyRecordDatum : historyRecordData) {
            double value = historyRecordDatum.getValue();
            min = Math.min(min, value);
            sum += value;
            max = Math.max(max, value);
        }

        return new Metric(category, min, max, sum / historyRecordData.size());
    }
}
