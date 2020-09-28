package ua.arlabunakty.agent.transformer.servlet.http;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.arlabunakty.test.TestObject;

@ExtendWith(MockitoExtension.class)
class ContentLengthTrackingPrintWriterTest {

    private ContentLengthTrackingPrintWriter writer;
    @Mock
    private PrintWriter target;
    @Mock
    private WrittenContentTracker tracker;

    @BeforeEach
    void init () {
        writer = new ContentLengthTrackingPrintWriter(target, tracker);
    }

    @Test
    void shouldDelegatePrintWriterCallsToDelegate() {
        writer.print(Integer.MAX_VALUE);
        verify(target).print(Integer.MAX_VALUE);

        writer.print(true);
        verify(target).print(true);

        writer.print(false);
        verify(target).print(false);

        writer.print('A');
        verify(target).print('A');

        writer.print(Long.MAX_VALUE);
        verify(target).print(Long.MAX_VALUE);

        writer.print(0.001f);
        verify(target).print(0.001f);

        writer.print("testString".toCharArray());
        verify(target).print("testString".toCharArray());

        writer.print(0.12345d);
        verify(target).print(0.12345d);

        writer.print("string");
        verify(target).print("string");

        writer.print((String) null);
        verify(target).print((String) null);

        TestObject testObject = new TestObject("bla-bla");
        writer.print(testObject);
        verify(target).print(eq(testObject));

        writer.print((Object) null);
        verify(target).print((Object) null);

        writer.println(Integer.MAX_VALUE);
        verify(target).println(Integer.MAX_VALUE);

        writer.println(true);
        verify(target).println(true);

        writer.println(false);
        verify(target).println(false);

        writer.println('A');
        verify(target).println('A');

        writer.println(Long.MAX_VALUE);
        verify(target).println(Long.MAX_VALUE);

        writer.println(0.001f);
        verify(target).println(0.001f);

        writer.println("testString".toCharArray());
        verify(target).println("testString".toCharArray());

        writer.println(0.12345d);
        verify(target).println(0.12345d);

        writer.println("string");
        verify(target).println("string");

        writer.println((String) null);
        verify(target).println((String) null);

        writer.println(testObject);
        verify(target).println(testObject);

        writer.println((Object) null);
        verify(target).println((Object) null);

        writer.println();
        verify(target).println();

        writer.append('A');
        verify(target).append('A');

        writer.append("testString");
        verify(target).append("testString");

        writer.append("testString", 0, 4);
        verify(target).append("testString", 0, 4);

        writer.write((int) 'a');
        verify(target).write((int) 'a');

        writer.write('A');
        verify(target).write('A');

        writer.write("testString".toCharArray());
        verify(target).write("testString".toCharArray());

        writer.write("testString".toCharArray(), 0, 4);
        verify(target).write("testString".toCharArray(), 0, 4);

        writer.write("string");
        verify(target).write("string");

        writer.write("string", 0, 4);
        verify(target).write("string", 0, 4);

        writer.checkError();
        verify(target).checkError();

        writer.close();
        verify(target).close();

        writer.flush();
        verify(target).flush();

    }

    @Test
    void shouldCallTrackerOnPrintInteger() {
        writer.print(Integer.MAX_VALUE);
        verify(tracker).trackContentLength(Integer.MAX_VALUE);
    }

    @Test
    void shouldCallTrackerOnPrintTrue() {
        writer.print(true);
        verify(tracker).trackContentLength(true);
    }

    @Test
    void shouldCallTrackerOnPrintFalse() {
        writer.print(false);
        verify(tracker).trackContentLength(false);
    }

    @Test
    void shouldCallTrackerOnPrintChar() {
        writer.print('A');
        verify(tracker).trackContentLength('A');
    }

    @Test
    void shouldCallTrackerOnPrintLong() {
        writer.print(Long.MAX_VALUE);
        verify(tracker).trackContentLength(Long.MAX_VALUE);
    }

    @Test
    void shouldCallTrackerOnPrintFloat() {
        writer.print(0.001f);
        verify(tracker).trackContentLength(0.001f);
    }

    @Test
    void shouldCallTrackerOnPrintCharArray() {
        writer.print("testString".toCharArray());
        verify(tracker).trackContentLength("testString".toCharArray());
    }

    @Test
    void shouldCallTrackerOnPrintDouble() {
        writer.print(0.12345d);
        verify(tracker).trackContentLength(0.12345d);
    }

    @Test
    void shouldCallTrackerOnPrintString() {
        writer.print("string");
        verify(tracker).trackContentLength("string");
    }

    @Test
    void shouldCallTrackerOnPrintNullString() {
        writer.print((String) null);
        verify(tracker).trackContentLength((String) null);
    }

    @Test
    void shouldCallTrackerOnPrintObject() {
        TestObject testObject = new TestObject("bla-bla");

        writer.print(testObject);

        verify(tracker).trackContentLength(eq(testObject));
    }

    @Test
    void shouldCallTrackerOnPrintNullObject() {
        writer.print((Object) null);
        verify(tracker).trackContentLength((Object) null);
    }

    @Test
    void shouldCallTrackerOnPrintLnInteger() {
        writer.println(Integer.MAX_VALUE);

        verify(tracker).trackContentLength(Integer.MAX_VALUE);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnTrue() {
        writer.println(true);

        verify(tracker).trackContentLength(true);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnFalse() {
        writer.println(false);

        verify(tracker).trackContentLength(false);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnChar() {
        writer.println('A');

        verify(tracker).trackContentLength('A');
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnLong() {
        writer.println(Long.MAX_VALUE);

        verify(tracker).trackContentLength(Long.MAX_VALUE);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnFloat() {
        writer.println(0.001f);

        verify(tracker).trackContentLength(0.001f);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnCharArray() {
        writer.println("testString".toCharArray());

        verify(tracker).trackContentLength("testString".toCharArray());
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnDouble() {
        writer.println(0.12345d);

        verify(tracker).trackContentLength(0.12345d);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnString() {
        writer.println("string");

        verify(tracker).trackContentLength("string");
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnNullString() {
        writer.println((String) null);

        verify(tracker).trackContentLength((String) null);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnObject() {
        TestObject testObject = new TestObject("bla-bla");

        writer.println(testObject);

        verify(tracker).trackContentLength(testObject);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnNullObject() {
        writer.println((Object) null);

        verify(tracker).trackContentLength((Object) null);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLn() {
        writer.println();
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnAppendChar() {
        writer.append('A');
        verify(tracker).trackContentLength('A');
    }

    @Test
    void shouldCallTrackerOnAppendString() {
        writer.append("testString");
        verify(tracker).addWrittenContent(10);
    }

    @Test
    void shouldCallTrackerOnAppendSubString() {
        writer.append("testString", 0, 4);
        verify(tracker).addWrittenContent(4);
    }

    @Test
    void shouldCallTrackerOnWriteIntChar() {
        writer.write((int) 'a');
        verify(tracker).addWrittenContent(1);
    }

    @Test
    void shouldCallTrackerOnWriteCharArray() {
        writer.write("testString".toCharArray());
        verify(tracker).trackContentLength("testString".toCharArray());
    }

    @Test
    void shouldCallTrackerOnWriteSubArray() {
        writer.write("testString".toCharArray(), 0, 4);
        verify(tracker).addWrittenContent(4);
    }

    @Test
    void shouldCallTrackerOnWriteString() {
        writer.write("string");
        verify(tracker).trackContentLength("string");
    }

    @Test
    void shouldCallTrackerOnWriteSubString() {
        writer.write("string", 0, 4);
        verify(tracker).addWrittenContent(4);
    }

}