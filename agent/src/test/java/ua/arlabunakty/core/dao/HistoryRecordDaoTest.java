package ua.arlabunakty.core.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.arlabunakty.core.domain.HistoryRecord;
import ua.arlabunakty.test.TestCategoryConstant;
import ua.arlabunakty.test.TestTagConstant;

class HistoryRecordDaoTest {

    private HistoryDao historyDao;

    @BeforeEach
    void init() {
        historyDao = new HistoryDao();
    }

    @Test
    void shouldAppendWithoutException() {
        assertDoesNotThrow(() ->
                historyDao.append(new HistoryRecord(0.0,
                        TestCategoryConstant.CATEGORY, TestTagConstant.TAG)));
    }

    @Test
    void shouldReturnHistoryDataByTag() {
        Collection<HistoryRecord> historyRecordData = Arrays.asList(
                new HistoryRecord(0.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(30.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(40.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.ANOTHER_TAG),
                new HistoryRecord(50.0, TestCategoryConstant.ANOTHER_CATEGORY));

        historyRecordData.forEach(historyDao::append);

        Collection<HistoryRecord> historyRecordByTag = historyDao.findByTag(TestTagConstant.TAG);

        assertIterableEquals(
                Arrays.asList(new HistoryRecord(0.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryRecord(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryRecord(20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryRecord(30.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG)),
                historyRecordByTag);
    }

    @Test
    void shouldReturnEmptyCollectionWhenNoHistoryDataMatchesByTag() {
        Collection<HistoryRecord> historyRecordByTag = historyDao.findByTag(TestTagConstant.TAG);

        assertIterableEquals(Collections.emptyList(), historyRecordByTag);
    }

    @Test
    void shouldReturnHistoryDataByCategory() {
        Collection<HistoryRecord> historyRecordData = Arrays.asList(
                new HistoryRecord(-10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(-20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(-30.0, TestCategoryConstant.CATEGORY),
                new HistoryRecord(-40.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG),
                new HistoryRecord(-50.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.ANOTHER_TAG),
                new HistoryRecord(-60.0, TestCategoryConstant.ANOTHER_CATEGORY));

        historyRecordData.forEach(historyDao::append);

        Collection<HistoryRecord> historyRecordByCategory =
                historyDao.findByCategory(TestCategoryConstant.CATEGORY);

        assertIterableEquals(
                Arrays.asList(new HistoryRecord(-10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryRecord(-20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryRecord(-30.0, TestCategoryConstant.CATEGORY)),
                historyRecordByCategory);
    }

    @Test
    void shouldReturnEmptyCollectionWhenNoHistoryDataMatchesByCategory() {
        Collection<HistoryRecord> historyRecordByCategory =
                historyDao.findByCategory(TestCategoryConstant.CATEGORY);

        assertIterableEquals(Collections.emptyList(), historyRecordByCategory);
    }
}