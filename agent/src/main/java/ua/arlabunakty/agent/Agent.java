package ua.arlabunakty.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.arlabunakty.agent.transformer.servlet.http.HttpServletTransformer;
import ua.arlabunakty.httpserver.EmbeddedHttpServer;

public final class Agent {
    private static final Logger LOGGER = Logger.getLogger(Agent.class.getName());
    private static final String HTTP_HTTP_SERVLET_CLASS_NAME = "javax.servlet.http.HttpServlet";

    private Agent() {

    }

    /**
     * This is called via the Java 1.5 Instrumentation startup sequence (JSR 163). Boot up the agent.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        LOGGER.fine("[Agent] premain method");

        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(HTTP_HTTP_SERVLET_CLASS_NAME)) {
                transform(clazz, instrumentation);
                return;
            }
        }
        ClassFileTransformer cft = new HttpServletTransformer(HTTP_HTTP_SERVLET_CLASS_NAME);
        instrumentation.addTransformer(cft, true);

        EmbeddedHttpServer.start();
    }

    private static void transform(Class<?> clazz, Instrumentation instrumentation) {
        LOGGER.info("[Agent] transform loaded class");

        ClassFileTransformer cft = new HttpServletTransformer(clazz);
        instrumentation.addTransformer(cft, true);
        try {
            instrumentation.retransformClasses(clazz);
            LOGGER.log(Level.FINE, "Class [{}] has been re-transformed", clazz);
        } catch (Exception ex) {
            throw new IllegalStateException("Transform failed for class: [" + clazz.getName() + "]", ex);
        }
    }
}
