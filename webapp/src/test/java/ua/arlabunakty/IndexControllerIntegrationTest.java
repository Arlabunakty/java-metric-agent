package ua.arlabunakty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IndexControllerIntegrationTest {

    private static final String X_METRIC_TRACE_ID_HEADER_NAME = "X-Metric-Trace-Id";

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    void shouldJavaAgentInterceptCallAndRegisterMetricForIndexPage() {
        assertMetricsForEndpoint(base.toString());
    }

    @Test
    void shouldJavaAgentInterceptCallAndRegisterMetricForIndexPageWithUserGreeting() {
        assertMetricsForEndpoint(base.toString() + "hello?name=username");
    }

    private void assertMetricsForEndpoint(String url) {
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        assertTrue(response.getHeaders()
                .containsKey(X_METRIC_TRACE_ID_HEADER_NAME), "response should have trace header");

        String traceId = getTraceId(response);
        MetricsByTraceId metricsByTraceId = getMetricsByTraceId(traceId);

        assertEquals(getResponseBodyLength(response), metricsByTraceId.getResponseBodyLength());
        assertTrue(metricsByTraceId.getRequestOperationTime() > 0);
    }

    private String getTraceId(ResponseEntity<String> response) {
        return Optional.ofNullable(response.getHeaders()
                .get(X_METRIC_TRACE_ID_HEADER_NAME))
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .orElse("");
    }

    private MetricsByTraceId getMetricsByTraceId(String traceId) {
        String metricsHtmlPage = template.getForEntity("http://localhost:8081/?id=" + traceId, String.class)
                .getBody();

        assertNotNull(metricsHtmlPage, "Metric page should be non null");

        Document document = Jsoup.parse(metricsHtmlPage);
        long requestOperationTime = (long) Double.parseDouble(
                document.select("td.requestOperationTime").text());
        long responseBodyLength = (long) Double.parseDouble(document.select("td.responseBodyLength").text());

        return new MetricsByTraceId(requestOperationTime, responseBodyLength);
    }

    private long getResponseBodyLength(ResponseEntity<String> response) {
        return (long) Optional.ofNullable(response.getBody())
                .map(String::length)
                .orElse(0);
    }

    private static class MetricsByTraceId {
        private final long requestOperationTime;
        private final long responseBodyLength;

        private MetricsByTraceId(long requestOperationTime, long responseBodyLength) {
            this.requestOperationTime = requestOperationTime;
            this.responseBodyLength = responseBodyLength;
        }

        public long getRequestOperationTime() {
            return requestOperationTime;
        }

        public long getResponseBodyLength() {
            return responseBodyLength;
        }
    }
}