package ua.arlabunakty.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.arlabunakty.core.dao.HistoryDao;
import ua.arlabunakty.core.domain.HistoryRecord;
import ua.arlabunakty.core.domain.Metric;
import ua.arlabunakty.test.TestCategoryConstant;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    private MetricService metricService;
    @Mock
    private StatsService statsService;

    @BeforeEach
    void init() {
        metricService = new MetricService(statsService);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenConstructing() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> new MetricService(null));

        assertEquals("statsService should be non null", exception.getMessage());
    }

    @Test
    void shouldReturnAggregatedMetricWhenGetMetricByCategory() {
        Mockito.when(statsService.findByCategory(TestCategoryConstant.CATEGORY))
                .thenReturn(Arrays.asList(
                        new HistoryRecord(10, TestCategoryConstant.CATEGORY),
                        new HistoryRecord(20, TestCategoryConstant.CATEGORY),
                        new HistoryRecord(30, TestCategoryConstant.CATEGORY)));

        Metric category = metricService.getMetricByCategory(TestCategoryConstant.CATEGORY);

        assertEquals(category, new Metric(TestCategoryConstant.CATEGORY, 10.0, 30.0, 20.0));
    }

    @Test
    void shouldReturnNullWhenGetMetricByMissingCategory() {
        Mockito.when(statsService.findByCategory(TestCategoryConstant.CATEGORY))
                .thenReturn(Collections.emptyList());

        Metric metric = metricService.getMetricByCategory(TestCategoryConstant.CATEGORY);

        assertNull(metric, "metricModel should be null for missing category");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetMetricByNullCategory() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> metricService.getMetricByCategory(null));

        assertEquals("category should be non null", exception.getMessage());
    }
}