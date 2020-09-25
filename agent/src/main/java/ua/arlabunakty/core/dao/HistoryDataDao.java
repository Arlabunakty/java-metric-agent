package ua.arlabunakty.core.dao;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import ua.arlabunakty.core.model.HistoryDataModel;

public class HistoryDataDao {
    private final Collection<HistoryDataModel> collection = new ConcurrentLinkedQueue<>();

    public void append(HistoryDataModel historyDataModel) {
        collection.add(historyDataModel);
    }

    public Collection<HistoryDataModel> findByTag(String tag) {
        return collection.stream()
                .filter(element -> element.containTag(tag))
                .collect(Collectors.toList());
    }

    public Collection<HistoryDataModel> findByCategory(String category) {
        return collection.stream()
                .filter(element -> category.equals(element.getCategory()))
                .collect(Collectors.toList());
    }
}
