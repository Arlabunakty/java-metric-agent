package ua.arlabunakty.core.service;

import java.util.Objects;
import ua.arlabunakty.core.dao.HistoryDao;

/**
 * Class which provides access to initialized Metric Core services:
 * - {@link StatsService};
 * - {@link MetricService}.
 */
public class ServiceFactory {
    private final StatsService statsService;
    private final MetricService metricService;
    private final ConfigurationService configurationService;

    ServiceFactory(StatsService statsService, MetricService metricService, ConfigurationService configurationService) {
        Objects.requireNonNull(statsService, "statsService should be non null");
        Objects.requireNonNull(metricService, "metricService should be non null");
        Objects.requireNonNull(configurationService, "configurationService should be non null");

        this.statsService = statsService;
        this.metricService = metricService;
        this.configurationService = configurationService;
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

    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    private static final class ServiceFactoryHolder {

        private static final ServiceFactory SERVICE_FACTORY;

        static {
            HistoryDao historyDao = new HistoryDao();
            StatsService statsService = new StatsService(historyDao);

            MetricService metricService = new MetricService(statsService);

            ConfigurationService configurationService = new ConfigurationService();

            SERVICE_FACTORY = new ServiceFactory(statsService, metricService, configurationService);
        }

    }
}
