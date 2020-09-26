package ua.arlabunakty.httpserver;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Headers;

public class EmbeddedHttpServer {

    public static void start() {
        Undertow.builder()
                .addHttpListener(8081, "localhost")
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