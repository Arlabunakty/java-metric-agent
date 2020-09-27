package ua.arlabunakty.core.dao;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import ua.arlabunakty.core.model.HistoryDataModel;

public class HistoryDataDao {
    private final Collection<HistoryDataModel> collection = new ConcurrentLinkedQueue<>();

    /**
     * Appends the specified history data to the Data Store.
     *
     * @param historyDataModel history data model.
     */
    public void append(HistoryDataModel historyDataModel) {
        collection.add(historyDataModel);
    }

    /**
     * Select all history data with given tag.
     *
     * @param tag - tag.
     * @return the collection of history data for the given tag.
     */
    public Collection<HistoryDataModel> findByTag(String tag) {
        return collection.stream()
                .filter(element -> element.containTag(tag))
                .collect(Collectors.toList());
    }

    /**
     * Selects all history data with given category.
     *
     * @param category - category.
     * @return the collection of history data for the given category.
     */
    public Collection<HistoryDataModel> findByCategory(String category) {
        return collection.stream()
                .filter(element -> element.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}
