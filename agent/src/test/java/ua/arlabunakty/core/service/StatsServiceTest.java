package ua.arlabunakty.core.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.arlabunakty.core.dao.HistoryDataDao;
import ua.arlabunakty.core.model.HistoryDataModel;
import ua.arlabunakty.core.model.TimerModel;
import ua.arlabunakty.test.TestCategoryConstant;
import ua.arlabunakty.test.TestTagConstant;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    private StatsService statsService;
    @Mock
    private HistoryDataDao historyDataDao;

    @BeforeEach
    void init() {
        statsService = new StatsService(historyDataDao);
    }

    @Test
    void shouldAppendHistoryDataWhenRecordValueWithSingleTag() {
        statsService.recordValue(10L, TestCategoryConstant.CATEGORY, TestTagConstant.TAG);

        verify(historyDataDao)
                .append(eq(new HistoryDataModel(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG)));
    }

    @Test
    void shouldAppendHistoryDataWhenRecordValueWithMultipleTags() {
        statsService.recordValue(11L, TestCategoryConstant.ANOTHER_CATEGORY, "tag1", "tag2");

        verify(historyDataDao)
                .append(eq(new HistoryDataModel(11.0, TestCategoryConstant.ANOTHER_CATEGORY, "tag1", "tag2")));
    }

    @Test
    void shouldAppendHistoryDataWhenRecordValueWithoutTags() {
        statsService.recordValue(12L, TestCategoryConstant.ANOTHER_CATEGORY);

        verify(historyDataDao)
                .append(eq(new HistoryDataModel(12.0, TestCategoryConstant.ANOTHER_CATEGORY)));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRecordValueWithNullCategory() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> statsService.recordValue(0L, null));

        assertEquals("category should be non null", exception.getMessage());
    }

    @Test
    void shouldReturnTagHistoryDataWhenFindByTag() {
        List<HistoryDataModel> tagHistoryData = Arrays.asList(
                new HistoryDataModel(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(11.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG));

        Mockito.lenient()
                .when(historyDataDao.findByTag(TestTagConstant.TAG))
                .thenReturn(tagHistoryData);

        Collection<HistoryDataModel> historyDataByTag = statsService.findByTag(TestTagConstant.TAG);

        assertIterableEquals(tagHistoryData, historyDataByTag);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenFindByNullTag() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> statsService.findByTag(null));

        assertEquals("tag should be non null", exception.getMessage());
    }

    @Test
    void shouldReturnNotNullTimerWhenRegisterTimer() {
        TimerModel timerModel = statsService.registerTimer(TestCategoryConstant.CATEGORY);

        assertNotNull(timerModel, "registered timer should be not null");
    }

    @Test
    void shouldRecordValueWhenRegisterTimerStopped() {
        TimerModel timerModel = statsService.registerTimer(TestCategoryConstant.CATEGORY, TestTagConstant.TAG);
        ArgumentCaptor<HistoryDataModel> captor = ArgumentCaptor.forClass(HistoryDataModel.class);

        timerModel.recordTimeInterval();
        verify(historyDataDao, times(1))
                .append(captor.capture());
        HistoryDataModel model = captor.getValue();

        assertAll(() -> assertTrue(model.getValue() > 0, "HistoryDataModel should have positive value"),
                () -> assertEquals(TestCategoryConstant.CATEGORY, model.getCategory()),
                () -> assertArrayEquals(new String[]{TestTagConstant.TAG}, model.getTags()));
    }
}