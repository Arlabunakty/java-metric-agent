package ua.arlabunakty.agent.transformer.servlet.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentLengthTrackingResponseWrapperTest {

    private WrittenContentTracker tracker;
    private ContentLengthTrackingResponseWrapper response;
    @Mock
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void init() {
        tracker = new WrittenContentTracker();
        response = new ContentLengthTrackingResponseWrapper(httpServletResponse, tracker);
    }

    @Test
    void shouldReturnCustomPrintWriter() throws IOException {
        when(httpServletResponse.getWriter())
                .thenReturn(mock(PrintWriter.class));
        PrintWriter writer = response.getWriter();

        assertTrue(writer instanceof ContentLengthTrackingPrintWriter);
        assertEquals(tracker, ((ContentLengthTrackingPrintWriter) writer).getTracker());
    }

    @Test
    void shouldReturnCustomOutputStream() throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();

        assertTrue(outputStream instanceof ContentLengthTrackingServletOutputStream);
        assertEquals(tracker, ((ContentLengthTrackingServletOutputStream) outputStream).getTracker());
    }

    @Test
    void shouldReturnZeroLengthWhenNoAction() {
        assertEquals(0, response.getContentLength());
    }

}
