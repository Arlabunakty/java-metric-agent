package ua.arlabunakty.core.service;

import ua.arlabunakty.core.dao.HistoryDataDao;

public class ServiceFactory {

    private final StatsService statsService;
    private final MetricService metricService;

    public ServiceFactory(StatsService statsService, MetricService metricService) {
        this.statsService = statsService;
        this.metricService = metricService;
    }

    public static ServiceFactory getInstance() {
        return ServiceFactoryHolder.serviceFactory;
    }

    public StatsService getStatsService() {
        return statsService;
    }

    public MetricService getMetricService() {
        return metricService;
    }

    private static final class ServiceFactoryHolder {

        private static final ServiceFactory serviceFactory;

        static {
            HistoryDataDao historyDataDao = new HistoryDataDao();
            StatsService statsService = new StatsService(historyDataDao);

            MetricService metricService = new MetricService(historyDataDao);

            serviceFactory = new ServiceFactory(statsService, metricService);
        }

    }
}
