package ua.arlabunakty.core.model;

import java.util.Arrays;
import java.util.Objects;

public final class HistoryDataModel {
    private final double value;
    private final String category;
    private final String[] tags;

    public HistoryDataModel(double value, String category, String... tags) {
        this.value = value;
        this.category = category;
        this.tags =  Arrays.copyOf(tags, tags.length);
    }

    public double getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public String[] getTags() {
        return Arrays.copyOf(tags, tags.length);
    }

    public boolean containTag(String tag) {
        for (String existingTag : tags) {
            if (tag.equals(existingTag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryDataModel model = (HistoryDataModel) o;
        return Double.compare(model.getValue(), getValue()) == 0 &&
                getCategory().equals(model.getCategory()) &&
                Arrays.equals(tags, model.tags);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getValue(), getCategory());
        result = 31 * result + Arrays.hashCode(tags);
        return result;
    }
}
