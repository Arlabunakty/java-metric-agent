package ua.arlabunakty.agent.transformer.servlet.http;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.arlabunakty.test.TestObject;

@ExtendWith(MockitoExtension.class)
class ContentLengthTrackingResponseWrapperTest {

    private ContentLengthTrackingResponseWrapper response;

    @Mock
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void init() {
        response = new ContentLengthTrackingResponseWrapper(httpServletResponse);
    }

    @Test
    void shouldDelegateAndTrackAllWrittenContentViaPrint() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(httpServletResponse.getWriter())
                .thenReturn(printWriter);
        PrintWriter responseWriter = response.getWriter();

        responseWriter.print(Integer.MAX_VALUE);
        responseWriter.print(true);
        responseWriter.print(false);
        responseWriter.print('A');
        responseWriter.print(Long.MAX_VALUE);
        responseWriter.print(0.001f);
        responseWriter.print("testString".toCharArray());
        responseWriter.print(0.12345d);
        responseWriter.print("string");
        responseWriter.print((String) null);
        responseWriter.print(new TestObject("bla-bla"));
        responseWriter.print((Object) null);

        assertAll(() -> assertEquals(82, response.getContentLength()),
                () -> assertEquals(
                        "2147483647truefalseA92233720368547758070.001testString0.12345stringnullbla-blanull",
                        stringWriter.toString()));
    }

    @Test
    void shouldDelegateAndTrackAllWrittenContentViaPrintLn() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(httpServletResponse.getWriter())
                .thenReturn(printWriter);
        PrintWriter responseWriter = response.getWriter();

        responseWriter.println(Integer.MAX_VALUE);
        responseWriter.println(true);
        responseWriter.println(false);
        responseWriter.println('A');
        responseWriter.println(Long.MAX_VALUE);
        responseWriter.println(0.001f);
        responseWriter.println("testString".toCharArray());
        responseWriter.println(0.12345d);
        responseWriter.println("string");
        responseWriter.println((String) null);
        responseWriter.println(new TestObject("bla-bla"));
        responseWriter.println((Object) null);
        responseWriter.println();

        assertAll(() -> assertEquals(108, response.getContentLength()),
                () -> assertEquals(
                        "2147483647" + System.lineSeparator() +
                                "true" + System.lineSeparator() +
                                "false" + System.lineSeparator() +
                                "A" + System.lineSeparator() +
                                "9223372036854775807" + System.lineSeparator() +
                                "0.001" + System.lineSeparator() +
                                "testString" + System.lineSeparator() +
                                "0.12345" + System.lineSeparator() +
                                "string" + System.lineSeparator() +
                                "null" + System.lineSeparator() +
                                "bla-bla" + System.lineSeparator() +
                                "null" + System.lineSeparator() +
                                System.lineSeparator(),
                        stringWriter.toString()));
    }

    @Test
    void shouldDelegateAndTrackAllWrittenContentViaWrite() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(httpServletResponse.getWriter())
                .thenReturn(printWriter);
        PrintWriter responseWriter = response.getWriter();

        responseWriter.write((int) 'a');
        responseWriter.write('A');
        responseWriter.write("testString".toCharArray());
        responseWriter.write("testString".toCharArray(), 0, 4);
        responseWriter.write("string");
        responseWriter.write("string", 0, 4);

        assertAll(() -> assertEquals(26, response.getContentLength()),
                () -> assertEquals(
                        "aAtestStringteststringstri",
                        stringWriter.toString()));
    }

    @Test
    void shouldReturnZeroLengthWhenNoAction() {
        assertEquals(0, response.getContentLength());
    }

}
