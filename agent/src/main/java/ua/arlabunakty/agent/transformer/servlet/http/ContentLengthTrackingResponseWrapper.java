package ua.arlabunakty.agent.transformer.servlet.http;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ContentLengthTrackingResponseWrapper extends HttpServletResponseWrapper {

    private final WrittenContentTracker tracker = new WrittenContentTracker();

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response the {@link HttpServletResponse} to be wrapped.
     * @throws IllegalArgumentException if the response is null
     */
    public ContentLengthTrackingResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new ContentLengthTrackingPrintWriter(super.getWriter(), tracker);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ContentLengthTrackingServletOutputStream(super.getOutputStream(), tracker);
    }

    public long getContentLength() {
        return tracker.getContentLength();
    }

}
