package ua.arlabunakty.core.service;

public class ConfigurationService {

    private static final int DEFAULT_EMBEDDED_SERVER_PORT = 8081;

    public int getEmbeddedHttpServerPort() {
        return DEFAULT_EMBEDDED_SERVER_PORT;
    }
}
