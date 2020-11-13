package ua.arlabunakty.httpserver;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Headers;
import ua.arlabunakty.core.service.ServiceFactory;

public final class EmbeddedHttpServer {

    private EmbeddedHttpServer() {

    }

    /**
     * Starts embedded Http server on port defined in {@link ua.arlabunakty.core.service.ConfigurationService},
     * which displays metrics and history data by Trace Id.
     */
    public static void start() {
        final int embeddedHttpServerPort = ServiceFactory.getInstance()
                .getConfigurationService()
                .getEmbeddedHttpServerPort();

        Undertow.builder()
                .addHttpListener(embeddedHttpServerPort, "localhost")
                .setHandler(
                        new RoutingHandler()
                                .get("/", IndexHttpHandler.getInstance()
                                        .indexHttpHandler())
                                .setFallbackHandler(notFoundHttpHandler()))
                .build()
                .start();
    }

    private static HttpHandler notFoundHttpHandler() {
        return exchange -> {
            exchange.setStatusCode(404);
            exchange.getResponseHeaders()
                    .put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender()
                    .send("Page Not Found!!");
        };
    }
}
