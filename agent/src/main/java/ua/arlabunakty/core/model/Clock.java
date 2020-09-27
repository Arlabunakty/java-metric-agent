package ua.arlabunakty.core.model;

public class Clock {
    /**
     * Providing access to the current instant, date and time.
     *
     * @return the difference, measured in milliseconds, between
     *          the current time and midnight, January 1, 1970 UTC.
     */
    public long getTimeInMilliseconds() {
        return System.currentTimeMillis();
    }
}
