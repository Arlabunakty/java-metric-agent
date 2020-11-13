package ua.arlabunakty.core.domain;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class is thread-safe.
 */
public final class HistoryRecord {
    private final double value;
    private final String category;
    private final String[] tags;

    /**
     * Constructs and initializes a historical data value for the given
     * category and tags.
     *
     * @param value - the historical data value of the newly constructed {@code HistoryDataModel}.
     * @param category - the category of the newly constructed {@code HistoryDataModel}.
     * @param tags - tags of the newly constructed {@code HistoryDataModel}.
     */
    public HistoryRecord(double value, String category, String... tags) {
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

    /**
     * Returns true if and only if this {@code HistoryDataModel} was tagged with the specified
     * tag name.
     *
     * @param tag - tag name.
     * @return true if this {@code HistoryDataModel} has tag {@code tag}, false otherwise.
     */
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
        HistoryRecord model = (HistoryRecord) o;
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
