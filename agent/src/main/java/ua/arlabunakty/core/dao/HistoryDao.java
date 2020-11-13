package ua.arlabunakty.core.dao;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import ua.arlabunakty.core.domain.HistoryRecord;

public class HistoryDao {
    private final Collection<HistoryRecord> collection = new ConcurrentLinkedQueue<>();

    /**
     * Appends the specified history data to the Data Store.
     *
     * @param historyRecord history data model.
     */
    public void append(HistoryRecord historyRecord) {
        collection.add(historyRecord);
    }

    /**
     * Select all history data with given tag.
     *
     * @param tag - tag.
     * @return the collection of history data for the given tag.
     */
    public Collection<HistoryRecord> findByTag(String tag) {
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
    public Collection<HistoryRecord> findByCategory(String category) {
        return collection.stream()
                .filter(element -> element.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}
