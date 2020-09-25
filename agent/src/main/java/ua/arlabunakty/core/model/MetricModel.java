package ua.arlabunakty.core.model;

import java.util.Objects;

public final class MetricModel {
    private final String category;
    private final double min;
    private final double max;
    private final double avg;

    public MetricModel(String category, double min, double max, double avg) {
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
        MetricModel that = (MetricModel) o;
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
