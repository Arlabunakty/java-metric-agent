package ua.arlabunakty.agent.transformer.servlet.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WrittenContentTrackerTest {

    private WrittenContentTracker tracker;

    @BeforeEach
    void init() {
        tracker = new WrittenContentTracker();
    }

    @Test
    void shouldTrackContentLengthForTrueBoolean() {
        tracker.trackContentLength(true);

        assertEquals(4, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForFalseBoolean() {
        tracker.trackContentLength(false);

        assertEquals(5, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForChar() {
        tracker.trackContentLength('A');

        assertEquals(1, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForObject() {
        Object object = new TestObject("string");

        tracker.trackContentLength(object);

        assertEquals(6, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForNullObject() {
        tracker.trackContentLength((Object) null);

        assertEquals(4, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForByteArray() {
        tracker.trackContentLength("test111".getBytes(StandardCharsets.UTF_8));

        assertEquals(7, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForEmptyByteArray() {
        tracker.trackContentLength(new byte[0]);

        assertEquals(0, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForCharVarArgs() {
        tracker.trackContentLength("test2222".toCharArray());

        assertEquals(8, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForIntZero() {
        tracker.trackContentLength(0);

        assertEquals(1, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForIntMaxValue() {
        tracker.trackContentLength(Integer.MAX_VALUE);

        assertEquals(10, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForFloatZero() {
        tracker.trackContentLength(0.0);

        assertEquals(3, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForFloatMinValue() {
        tracker.trackContentLength(Float.MIN_VALUE);

        assertEquals(7, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForDoubleZero() {
        tracker.trackContentLength(0.01d);

        assertEquals(4, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForDoubleMinValue() {
        tracker.trackContentLength(Double.MAX_VALUE);

        assertEquals(22, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForString() {
        tracker.trackContentLength("string");

        assertEquals(6, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthForNullString() {
        tracker.trackContentLength((String) null);

        assertEquals(4, tracker.getContentLength());
    }

    @Test
    void shouldTrackContentLengthNewLine() {
        tracker.trackContentLengthNewLine();

        assertEquals(2, tracker.getContentLength());
    }

    @Test
    void shouldAddWrittenContent() {
        tracker.addWrittenContent(10);

        assertEquals(10, tracker.getContentLength());
    }

    private static class TestObject {

        private final String value;

        TestObject(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}