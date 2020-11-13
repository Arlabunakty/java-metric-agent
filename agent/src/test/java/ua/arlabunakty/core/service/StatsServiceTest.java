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
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.arlabunakty.core.dao.HistoryDao;
import ua.arlabunakty.core.domain.HistoryRecord;
import ua.arlabunakty.core.domain.Timer;
import ua.arlabunakty.test.TestCategoryConstant;
import ua.arlabunakty.test.TestTagConstant;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    private StatsService statsService;
    @Mock
    private HistoryDao historyDao;

    @BeforeEach
    void init() {
        statsService = new StatsService(historyDao);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenConstructorWithNull() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> new StatsService(null));

        assertEquals("historyDataDao should be non null", exception.getMessage());
    }

    @Test
    void shouldAppendHistoryDataWhenRecordValueWithSingleTag() {
        statsService.recordValue(10L, TestCategoryConstant.CATEGORY, TestTagConstant.TAG);

        verify(historyDao)
                .append(eq(new HistoryRecord(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG)));
    }

    @Test
    void shouldAppendHistoryDataWhenRecordValueWithMultipleTags() {
        statsService.recordValue(11L, TestCategoryConstant.ANOTHER_CATEGORY, "tag1", "tag2");

        verify(historyDao)
                .append(eq(new HistoryRecord(11.0, TestCategoryConstant.ANOTHER_CATEGORY, "tag1", "tag2")));
    }

    @Test
    void shouldAppendHistoryDataWhenRecordValueWithoutTags() {
        statsService.recordValue(12L, TestCategoryConstant.ANOTHER_CATEGORY);

        verify(historyDao)
                .append(eq(new HistoryRecord(12.0, TestCategoryConstant.ANOTHER_CATEGORY)));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRecordValueWithNullCategory() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> statsService.recordValue(0L, null));

        assertEquals("category should be non null", exception.getMessage());
    }

    @Test
    void shouldReturnTagHistoryDataWhenFindByTag() {
        List<HistoryRecord> tagHistoryRecordData = Arrays.asList(
                new HistoryRecord(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(11.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG));

        Mockito.lenient()
                .when(historyDao.findByTag(TestTagConstant.TAG))
                .thenReturn(tagHistoryRecordData);

        Collection<HistoryRecord> historyRecordByTag = statsService.findByTag(TestTagConstant.TAG);

        assertIterableEquals(tagHistoryRecordData, historyRecordByTag);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenFindByNullTag() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> statsService.findByTag(null));

        assertEquals("tag should be non null", exception.getMessage());
    }

    @Test
    void shouldReturnNotNullTimerWhenRegisterTimer() {
        Timer timer = statsService.registerTimer(TestCategoryConstant.CATEGORY);

        assertNotNull(timer, "registered timer should be not null");
    }

    @Test
    void shouldRecordValueWhenRegisterTimerStopped() {
        Timer timer = statsService.registerTimer(TestCategoryConstant.CATEGORY, TestTagConstant.TAG);
        ArgumentCaptor<HistoryRecord> captor = ArgumentCaptor.forClass(HistoryRecord.class);

        timer.recordTimeInterval();
        verify(historyDao, times(1))
                .append(captor.capture());
        HistoryRecord model = captor.getValue();

        assertAll(() -> assertTrue(model.getValue() > 0, "HistoryDataModel should have positive value"),
                () -> assertEquals(TestCategoryConstant.CATEGORY, model.getCategory()),
                () -> assertArrayEquals(new String[]{TestTagConstant.TAG}, model.getTags()));
    }
}