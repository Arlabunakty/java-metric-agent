package ua.arlabunakty.agent.transformer.servlet.http;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Objects;

public class ContentLengthTrackingPrintWriter extends PrintWriter {
    private final PrintWriter delegate;
    private final WrittenContentTracker tracker;

    /**
     * Constructs a decorator {@code PrintWriter} around another {@code PrintWriter}
     * to track written content via print/write methods.
     *
     * @param delegate - the underlying {@code PrintWriter}.
     * @param tracker - who records passing content.
     * @throws NullPointerException  if the delegate or tracker is null.
     */
    public ContentLengthTrackingPrintWriter(PrintWriter delegate, WrittenContentTracker tracker) {
        super(delegate);

        Objects.requireNonNull(delegate, "delegate should be non null");
        Objects.requireNonNull(tracker, "tracker should be non null");

        this.delegate = delegate;
        this.tracker = tracker;
    }

    public WrittenContentTracker getTracker() {
        return tracker;
    }

    @Override
    public void flush() {
        this.delegate.flush();
    }

    @Override
    public void close() {
        this.delegate.close();
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

    @Override
    public boolean checkError() {
        return this.delegate.checkError();
    }

    @Override
    public void write(int c) {
        tracker.addWrittenContent(1);
        this.delegate.write(c);
    }

    @Override
    public void write(char[] buf, int off, int len) {
        tracker.addWrittenContent(len);
        this.delegate.write(buf, off, len);
    }

    @Override
    public void write(char[] buf) {
        tracker.trackContentLength(buf);
        this.delegate.write(buf);
    }

    @Override
    public void write(String s, int off, int len) {
        tracker.addWrittenContent(len);
        this.delegate.write(s, off, len);
    }

    @Override
    public void write(String s) {
        tracker.trackContentLength(s);
        this.delegate.write(s);
    }

    @Override
    public void print(boolean b) {
        tracker.trackContentLength(b);
        this.delegate.print(b);
    }

    @Override
    public void print(char c) {
        tracker.trackContentLength(c);
        this.delegate.print(c);
    }

    @Override
    public void print(int i) {
        tracker.trackContentLength(i);
        this.delegate.print(i);
    }

    @Override
    public void print(long l) {
        tracker.trackContentLength(l);
        this.delegate.print(l);
    }

    @Override
    public void print(float f) {
        tracker.trackContentLength(f);
        this.delegate.print(f);
    }

    @Override
    public void print(double d) {
        tracker.trackContentLength(d);
        this.delegate.print(d);
    }

    @Override
    public void print(char[] s) {
        tracker.trackContentLength(s);
        this.delegate.print(s);
    }

    @Override
    public void print(String s) {
        tracker.trackContentLength(s);
        this.delegate.print(s);
    }

    @Override
    public void print(Object obj) {
        tracker.trackContentLength(obj);
        this.delegate.print(obj);
    }

    @Override
    public void println() {
        tracker.trackContentLengthNewLine();
        this.delegate.println();
    }

    @Override
    public void println(boolean x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(char x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(int x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(long x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(float x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(double x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(char[] x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(String x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public void println(Object x) {
        tracker.trackContentLength(x);
        tracker.trackContentLengthNewLine();
        this.delegate.println(x);
    }

    @Override
    public PrintWriter printf(String format, Object... args) {
        return this.delegate.printf(format, args);
    }

    @Override
    public PrintWriter printf(Locale l, String format, Object... args) {
        return this.delegate.printf(l, format, args);
    }

    @Override
    public PrintWriter format(String format, Object... args) {
        return this.delegate.format(format, args);
    }

    @Override
    public PrintWriter format(Locale l, String format, Object... args) {
        return this.delegate.format(l, format, args);
    }

    @Override
    public PrintWriter append(CharSequence csq) {
        tracker.addWrittenContent(csq.length());
        return this.delegate.append(csq);
    }

    @Override
    public PrintWriter append(CharSequence csq, int start, int end) {
        tracker.addWrittenContent(end - start);
        return this.delegate.append(csq, start, end);
    }

    @Override
    public PrintWriter append(char c) {
        tracker.trackContentLength(c);
        return this.delegate.append(c);
    }
}
