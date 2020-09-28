package ua.arlabunakty.agent.transformer.servlet.http;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentLengthTrackingServletOutputStreamTest {

    private ContentLengthTrackingServletOutputStream outputStream;
    @Mock
    private ServletOutputStream target;
    @Mock
    private WrittenContentTracker tracker;

    @BeforeEach
    void init() {
        outputStream = new ContentLengthTrackingServletOutputStream(target, tracker);
    }

    @Test
    void shouldDelegatePrintWriterCallsToDelegate() throws IOException {
        outputStream.print(Integer.MAX_VALUE);
        verify(target).print(Integer.MAX_VALUE);

        outputStream.print(true);
        verify(target).print(true);

        outputStream.print(false);
        verify(target).print(false);

        outputStream.print('A');
        verify(target).print('A');

        outputStream.print(Long.MAX_VALUE);
        verify(target).print(Long.MAX_VALUE);

        outputStream.print(0.001f);
        verify(target).print(0.001f);

        outputStream.print(0.12345d);
        verify(target).print(0.12345d);

        outputStream.print("string");
        verify(target).print("string");

        outputStream.print((String) null);
        verify(target).print((String) null);

        outputStream.println(Integer.MAX_VALUE);
        verify(target).println(Integer.MAX_VALUE);

        outputStream.println(true);
        verify(target).println(true);

        outputStream.println(false);
        verify(target).println(false);

        outputStream.println('A');
        verify(target).println('A');

        outputStream.println(Long.MAX_VALUE);
        verify(target).println(Long.MAX_VALUE);

        outputStream.println(0.001f);
        verify(target).println(0.001f);

        outputStream.println(0.12345d);
        verify(target).println(0.12345d);

        outputStream.println("string");
        verify(target).println("string");

        outputStream.println((String) null);
        verify(target).println((String) null);

        outputStream.println();
        verify(target).println();

        outputStream.write((int) 'a');
        verify(target).write((int) 'a');

        outputStream.write("testString".getBytes(StandardCharsets.UTF_8));
        verify(target).write("testString".getBytes(StandardCharsets.UTF_8));

        outputStream.write("testString".getBytes(StandardCharsets.UTF_8), 0, 4);
        verify(target).write("testString".getBytes(StandardCharsets.UTF_8), 0, 4);

        outputStream.close();
        verify(target).close();

        outputStream.flush();
        verify(target).flush();

        outputStream.isReady();
        verify(target).isReady();

        outputStream.setWriteListener(null);
        verify(target).setWriteListener(null);
    }

    @Test
    void shouldCallTrackerOnPrintInteger() throws IOException {
        outputStream.print(Integer.MAX_VALUE);
        verify(tracker).trackContentLength(Integer.MAX_VALUE);
    }

    @Test
    void shouldCallTrackerOnPrintTrue() throws IOException {
        outputStream.print(true);
        verify(tracker).trackContentLength(true);
    }

    @Test
    void shouldCallTrackerOnPrintFalse() throws IOException {
        outputStream.print(false);
        verify(tracker).trackContentLength(false);
    }

    @Test
    void shouldCallTrackerOnPrintChar() throws IOException {
        outputStream.print('A');
        verify(tracker).trackContentLength('A');
    }

    @Test
    void shouldCallTrackerOnPrintLong() throws IOException {
        outputStream.print(Long.MAX_VALUE);
        verify(tracker).trackContentLength(Long.MAX_VALUE);
    }

    @Test
    void shouldCallTrackerOnPrintFloat() throws IOException {
        outputStream.print(0.001f);
        verify(tracker).trackContentLength(0.001f);
    }

    @Test
    void shouldCallTrackerOnPrintDouble() throws IOException {
        outputStream.print(0.12345d);
        verify(tracker).trackContentLength(0.12345d);
    }

    @Test
    void shouldCallTrackerOnPrintString() throws IOException {
        outputStream.print("string");
        verify(tracker).trackContentLength("string");
    }

    @Test
    void shouldCallTrackerOnPrintNullString() throws IOException {
        outputStream.print((String) null);
        verify(tracker).trackContentLength((String) null);
    }

    @Test
    void shouldCallTrackerOnPrintLnInteger() throws IOException {
        outputStream.println(Integer.MAX_VALUE);

        verify(tracker).trackContentLength(Integer.MAX_VALUE);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnTrue() throws IOException {
        outputStream.println(true);

        verify(tracker).trackContentLength(true);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnFalse() throws IOException {
        outputStream.println(false);

        verify(tracker).trackContentLength(false);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnChar() throws IOException {
        outputStream.println('A');

        verify(tracker).trackContentLength('A');
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnLong() throws IOException {
        outputStream.println(Long.MAX_VALUE);

        verify(tracker).trackContentLength(Long.MAX_VALUE);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnFloat() throws IOException {
        outputStream.println(0.001f);

        verify(tracker).trackContentLength(0.001f);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnDouble() throws IOException {
        outputStream.println(0.12345d);

        verify(tracker).trackContentLength(0.12345d);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnString() throws IOException {
        outputStream.println("string");

        verify(tracker).trackContentLength("string");
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnPrintLnNullString() throws IOException {
        outputStream.println((String) null);

        verify(tracker).trackContentLength((String) null);
        verify(tracker).trackContentLengthNewLine();
    }

    @Test
    void shouldCallTrackerOnWriteIntChar() throws IOException {
        outputStream.write((int) 'a');
        verify(tracker).addWrittenContent(1);
    }

    @Test
    void shouldCallTrackerOnWriteByteArray() throws IOException {
        outputStream.write("testString".getBytes(StandardCharsets.UTF_8));
        verify(tracker).trackContentLength("testString".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void shouldCallTrackerOnWriteSubByteArray() throws IOException {
        outputStream.write("testString".getBytes(StandardCharsets.UTF_8), 0, 4);
        verify(tracker).addWrittenContent(4);
    }

}