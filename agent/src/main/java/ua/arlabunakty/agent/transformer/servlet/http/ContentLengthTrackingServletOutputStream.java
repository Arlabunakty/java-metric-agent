package ua.arlabunakty.agent.transformer.servlet.http;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ContentLengthTrackingServletOutputStream extends ServletOutputStream {
    private final ServletOutputStream delegate;
    private final WrittenContentTracker tracker;

    public ContentLengthTrackingServletOutputStream(ServletOutputStream delegate, WrittenContentTracker tracker) {
        this.delegate = delegate;
        this.tracker = tracker;
    }


    public WrittenContentTracker getTracker() {
        return tracker;
    }

    @Override
    public void print(String s) throws IOException {
        tracker.trackContentLength(s);
        this.delegate.print(s);
    }

    @Override
    public void print(boolean b) throws IOException {
        tracker.trackContentLength(b);
        this.delegate.print(b);
    }

    @Override
    public void print(char c) throws IOException {
        tracker.trackContentLength(c);
        this.delegate.print(c);
    }

    @Override
    public void print(int i) throws IOException {
        tracker.trackContentLength(i);
        this.delegate.print(i);
    }

    @Override
    public void print(long l) throws IOException {
        tracker.trackContentLength(l);
        this.delegate.print(l);
    }

    @Override
    public void print(float f) throws IOException {
        tracker.trackContentLength(f);
        this.delegate.print(f);
    }

    @Override
    public void print(double d) throws IOException {
        tracker.trackContentLength(d);
        this.delegate.print(d);
    }

    @Override
    public void println() throws IOException {
        tracker.trackContentLengthNewLine();
        this.delegate.println();
    }

    @Override
    public void println(boolean b) throws IOException {
        tracker.trackContentLength(b);
        tracker.trackContentLengthNewLine();
        this.delegate.println(b);
    }

    @Override
    public void println(char c) throws IOException {
        tracker.trackContentLength(c);
        tracker.trackContentLengthNewLine();
        this.delegate.println(c);
    }

    @Override
    public void println(int i) throws IOException {
        tracker.trackContentLength(i);
        tracker.trackContentLengthNewLine();
        this.delegate.println(i);
    }

    @Override
    public void println(double d) throws IOException {
        tracker.trackContentLength(d);
        tracker.trackContentLengthNewLine();
        this.delegate.println(d);
    }

    @Override
    public void println(float f) throws IOException {
        tracker.trackContentLength(f);
        tracker.trackContentLengthNewLine();
        this.delegate.println(f);
    }

    @Override
    public void println(long l) throws IOException {
        tracker.trackContentLength(l);
        tracker.trackContentLengthNewLine();
        this.delegate.println(l);
    }

    @Override
    public void println(String s) throws IOException {
        tracker.trackContentLength(s);
        tracker.trackContentLengthNewLine();
        this.delegate.println(s);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        tracker.addWrittenContent(len);
        this.delegate.write(b, off, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
        tracker.trackContentLength(b);
        this.delegate.write(b);
    }

    @Override
    public void write(int b) throws IOException {
        tracker.addWrittenContent(1);
        this.delegate.write(b);
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public boolean isReady() {
        return this.delegate.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        this.delegate.setWriteListener(writeListener);
    }

    @Override
    public boolean equals(Object obj) {
        return this.delegate.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

}
