package ua.arlabunakty.core.domain;

import java.util.Objects;

/**
 * This class is thread-safe.
 */
public final class Metric {
    private final String category;
    private final double min;
    private final double max;
    private final double avg;

    /**
     * Constructs and initializes an aggregated metric data.
     *
     * @param category - the category of the aggregated data.
     * @param min - the minimum value of the aggregated data.
     * @param max - the maximum value of the aggregated data.
     * @param avg - the average value of the aggregated data.
     */
    public Metric(String category, double min, double max, double avg) {
        this.category = category;
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public String getCategory() {
        return category;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getAvg() {
        return avg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Metric that = (Metric) o;
        return Double.compare(that.getMin(), getMin()) == 0 &&
                Double.compare(that.getMax(), getMax()) == 0 &&
                Double.compare(that.getAvg(), getAvg()) == 0 &&
                getCategory().equals(that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getMin(), getMax(), getAvg());
    }
}
