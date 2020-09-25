package ua.arlabunakty.agent.transformer.servlet.http;

public class WrittenContentTracker {
    private long accumulator;

    public void trackContentLength(boolean content) {
        updateWrittenContent(content ? 4 : 5);
    }

    public void trackContentLength(char content) {
        updateWrittenContent(1);
    }

    public void trackContentLength(Object content) {
        trackContentLength(String.valueOf(content));
    }

    public void trackContentLength(byte[] content) {
        updateWrittenContent((content != null) ? content.length : 0);
    }

    public void trackContentLength(char... content) {
        updateWrittenContent((content != null) ? content.length : 0);
    }

    public void trackContentLength(int content) {
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
        updateWrittenContent(contentLength);
    }

    public void trackContentLengthNewLine() {
        trackContentLength("\r\n");
    }

    public void updateWrittenContent(long contentLengthToWrite) {
        accumulator += contentLengthToWrite;
    }

    public long getContentLength() {
        return accumulator;
    }
}
