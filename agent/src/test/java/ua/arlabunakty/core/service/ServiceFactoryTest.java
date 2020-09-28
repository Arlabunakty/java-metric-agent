package ua.arlabunakty.core.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ServiceFactoryTest {

    @Test
    void shouldReturnNonNullInstance() {
        assertNotNull(ServiceFactory.getInstance());
    }

    @Test
    void shouldReturnNonNullMetricService() {
        assertNotNull(ServiceFactory.getInstance().getMetricService());
    }

    @Test
    void shouldReturnNonNullStatsService() {
        assertNotNull(ServiceFactory.getInstance().getStatsService());
    }

}