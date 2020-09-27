package ua.arlabunakty.agent.transformer.servlet.http;

public class WrittenContentTracker {
    private long accumulator;

    public void trackContentLength(boolean content) {
        addWrittenContent(content ? 4 : 5);
    }

    public void trackContentLength(char content) {
        addWrittenContent(1);
    }

    public void trackContentLength(Object content) {
        trackContentLength(String.valueOf(content));
    }

    public void trackContentLength(byte[] content) {
        addWrittenContent(content == null ? 0 : content.length);
    }

    public void trackContentLength(char... content) {
        addWrittenContent(content == null ? 0 : content.length);
    }

    public void trackContentLength(int content) {
        trackContentLength(String.valueOf(content));
    }

    public void trackContentLength(long content) {
        trackContentLength(String.valueOf(content));
    }

    public void trackContentLength(float content) {
        trackContentLength(String.valueOf(content));
    }

    public void trackContentLength(double content) {
        trackContentLength(String.valueOf(content));
    }

    public void trackContentLength(String content) {
        int contentLength = (content != null) ? content.length() : 4;
        addWrittenContent(contentLength);
    }

    public void trackContentLengthNewLine() {
        trackContentLength("\r\n");
    }

    public void addWrittenContent(long contentLengthToWrite) {
        accumulator += contentLengthToWrite;
    }

    public long getContentLength() {
        return accumulator;
    }
}
