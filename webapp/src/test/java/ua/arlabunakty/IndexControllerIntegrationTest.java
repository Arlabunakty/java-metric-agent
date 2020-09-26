package ua.arlabunakty;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
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
    void indexPage() {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);

        assertAll(
                () -> assertTrue(response.getHeaders()
                        .containsKey("X-Metric-Trace-Id"), "response should have trace header"));
    }

    @Test
    void indexPageWithUserGreeting() {
        String url = base.toString() + "hello?name=username";

        ResponseEntity<String> response = template.getForEntity(url, String.class);

        assertAll(
                () -> assertTrue(response.getHeaders()
                        .containsKey("X-Metric-Trace-Id"), "response should have trace header"));
    }
}