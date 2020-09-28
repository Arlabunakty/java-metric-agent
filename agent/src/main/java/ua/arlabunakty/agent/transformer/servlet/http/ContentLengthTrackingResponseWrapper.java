package ua.arlabunakty.agent.transformer.servlet.http;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ContentLengthTrackingResponseWrapper extends HttpServletResponseWrapper {
    private final WrittenContentTracker tracker;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response the {@link HttpServletResponse} to be wrapped.
     */
    public ContentLengthTrackingResponseWrapper(HttpServletResponse response) {
        this(response, new WrittenContentTracker());
    }

    ContentLengthTrackingResponseWrapper(HttpServletResponse response, WrittenContentTracker tracker) {
        super(response);
        this.tracker = tracker;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new ContentLengthTrackingPrintWriter(super.getWriter(), tracker);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ContentLengthTrackingServletOutputStream(super.getOutputStream(), tracker);
    }

    /**
     * Returns number of bytes which was passed to {@link PrintWriter} and {@link ServletOutputStream}
     * on the moment of call.
     *
     * @return number of bytes to be returned with HttpResponse.
     */
    public long getContentLength() {
        return tracker.getContentLength();
    }

}
