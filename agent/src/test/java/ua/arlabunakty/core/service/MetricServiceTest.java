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
import ua.arlabunakty.core.dao.HistoryDataDao;
import ua.arlabunakty.core.model.HistoryDataModel;
import ua.arlabunakty.core.model.MetricModel;
import ua.arlabunakty.test.TestCategoryConstant;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    private MetricService metricService;
    @Mock
    private HistoryDataDao historyDataDao;

    @BeforeEach
    void init() {
        metricService = new MetricService(historyDataDao);
    }

    @Test
    void shouldReturnAggregatedMetricWhenGetMetricByCategory() {
        Mockito.when(historyDataDao.findByCategory(TestCategoryConstant.CATEGORY))
                .thenReturn(Arrays.asList(
                        new HistoryDataModel(10, TestCategoryConstant.CATEGORY),
                        new HistoryDataModel(20, TestCategoryConstant.CATEGORY),
                        new HistoryDataModel(30, TestCategoryConstant.CATEGORY)));

        MetricModel category = metricService.getMetricByCategory(TestCategoryConstant.CATEGORY);

        assertEquals(category, new MetricModel(TestCategoryConstant.CATEGORY, 10.0, 30.0, 20.0));
    }

    @Test
    void shouldReturnNullWhenGetMetricByMissingCategory() {
        Mockito.when(historyDataDao.findByCategory(TestCategoryConstant.CATEGORY))
                .thenReturn(Collections.emptyList());

        MetricModel metricModel = metricService.getMetricByCategory(TestCategoryConstant.CATEGORY);

        assertNull(metricModel, "metricModel should be null for missing category");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenGetMetricByNullCategory() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> metricService.getMetricByCategory(null));

        assertEquals("category should be non null", exception.getMessage());
    }
}