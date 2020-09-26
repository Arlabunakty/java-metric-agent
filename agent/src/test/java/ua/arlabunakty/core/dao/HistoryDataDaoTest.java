package ua.arlabunakty.core.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.arlabunakty.core.model.HistoryDataModel;
import ua.arlabunakty.test.TestCategoryConstant;
import ua.arlabunakty.test.TestTagConstant;

class HistoryDataDaoTest {

    private HistoryDataDao historyDataDao;

    @BeforeEach
    void init() {
        historyDataDao = new HistoryDataDao();
    }

    @Test
    void shouldAppendWithoutException() {
        assertDoesNotThrow(() ->
                historyDataDao.append(new HistoryDataModel(0.0,
                        TestCategoryConstant.CATEGORY, TestTagConstant.TAG)));
    }

    @Test
    void shouldReturnHistoryDataByTag() {
        Collection<HistoryDataModel> historyData = Arrays.asList(
                new HistoryDataModel(0.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(30.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(40.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.ANOTHER_TAG),
                new HistoryDataModel(50.0, TestCategoryConstant.ANOTHER_CATEGORY));

        historyData.forEach(historyDataDao::append);

        Collection<HistoryDataModel> historyDataByTag = historyDataDao.findByTag(TestTagConstant.TAG);

        assertIterableEquals(
                Arrays.asList(new HistoryDataModel(0.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryDataModel(10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryDataModel(20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryDataModel(30.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG)),
                historyDataByTag);
    }

    @Test
    void shouldReturnEmptyCollectionWhenNoHistoryDataMatchesByTag() {
        Collection<HistoryDataModel> historyDataByTag = historyDataDao.findByTag(TestTagConstant.TAG);

        assertIterableEquals(Collections.emptyList(), historyDataByTag);
    }

    @Test
    void shouldReturnHistoryDataByCategory() {
        Collection<HistoryDataModel> historyData = Arrays.asList(
                new HistoryDataModel(-10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(-20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(-30.0, TestCategoryConstant.CATEGORY),
                new HistoryDataModel(-40.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.TAG),
                new HistoryDataModel(-50.0, TestCategoryConstant.ANOTHER_CATEGORY, TestTagConstant.ANOTHER_TAG),
                new HistoryDataModel(-60.0, TestCategoryConstant.ANOTHER_CATEGORY));

        historyData.forEach(historyDataDao::append);

        Collection<HistoryDataModel> historyDataByCategory =
                historyDataDao.findByCategory(TestCategoryConstant.CATEGORY);

        assertIterableEquals(
                Arrays.asList(new HistoryDataModel(-10.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryDataModel(-20.0, TestCategoryConstant.CATEGORY, TestTagConstant.TAG),
                        new HistoryDataModel(-30.0, TestCategoryConstant.CATEGORY)),
                historyDataByCategory);
    }

    @Test
    void shouldReturnEmptyCollectionWhenNoHistoryDataMatchesByCategory() {
        Collection<HistoryDataModel> historyDataByCategory =
                historyDataDao.findByCategory(TestCategoryConstant.CATEGORY);

        assertIterableEquals(Collections.emptyList(), historyDataByCategory);
    }
}