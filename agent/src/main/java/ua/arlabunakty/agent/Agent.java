package ua.arlabunakty.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.arlabunakty.agent.transformer.servlet.http.HttpServletTransformer;
import ua.arlabunakty.httpserver.EmbeddedHttpServer;

public class Agent {
    private static final Logger LOGGER = Logger.getLogger(Agent.class.getName());

    /**
     * This is called via the Java 1.5 Instrumentation startup sequence (JSR 163). Boot up the agent.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        LOGGER.fine("[Agent] premain method");

        transformClass("javax.servlet.http.HttpServlet", instrumentation);

        EmbeddedHttpServer.start();
    }

    private static void transformClass(String className, Instrumentation instrumentation) {
        // see if we can get the class using forName
        try {
            Class<?> targetCls = Class.forName(className);
            transform(targetCls, instrumentation);
            return;
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "Class [{}] not found with Class.forName", className);
        }

        // otherwise iterate all loaded classes and find what we want
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(className)) {
                transform(clazz, instrumentation);
                return;
            }
        }
        throw new RuntimeException("Failed to find class [" + className + "]");
    }

    private static void transform(Class<?> clazz, Instrumentation instrumentation) {
        ClassFileTransformer cft = new HttpServletTransformer(clazz);
        instrumentation.addTransformer(cft, true);
        try {
            instrumentation.retransformClasses(clazz);
            LOGGER.log(Level.FINE, "Class [{}] has been re-transformed", clazz);
        } catch (Exception ex) {
            throw new RuntimeException("Transform failed for class: [" + clazz.getName() + "]", ex);
        }
    }
}
