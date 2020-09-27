package ua.arlabunakty.core.service;

import java.util.Objects;
import ua.arlabunakty.core.dao.HistoryDataDao;

/**
 * Class which provides access to initialized Metric Core services:
 * - {@link StatsService};
 * - {@link MetricService}.
 */
public class ServiceFactory {
    private final StatsService statsService;
    private final MetricService metricService;

    ServiceFactory(StatsService statsService, MetricService metricService) {
        Objects.requireNonNull(statsService, "statsService should be non null");
        Objects.requireNonNull(metricService, "metricService should be non null");

        this.statsService = statsService;
        this.metricService = metricService;
    }

    public static ServiceFactory getInstance() {
        return ServiceFactoryHolder.SERVICE_FACTORY;
    }

    public StatsService getStatsService() {
        return statsService;
    }

    public MetricService getMetricService() {
        return metricService;
    }

    private static final class ServiceFactoryHolder {

        private static final ServiceFactory SERVICE_FACTORY;

        static {
            HistoryDataDao historyDataDao = new HistoryDataDao();
            StatsService statsService = new StatsService(historyDataDao);

            MetricService metricService = new MetricService(historyDataDao);

            SERVICE_FACTORY = new ServiceFactory(statsService, metricService);
        }

    }
}
