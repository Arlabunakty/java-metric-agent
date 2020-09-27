package ua.arlabunakty.agent.transformer.servlet.http;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import ua.arlabunakty.core.model.WebCategoryEnum;

public class HttpServletTransformer implements ClassFileTransformer {

    private static final Logger LOGGER = Logger.getLogger(HttpServletTransformer.class.getName());
    private static final String TRANSFORMED_METHOD_NAME = "service";
    private static final String GET_TRACKING_ID_HEADER_EXPRESSION = "new String[]{$2.getHeader(\"X-Metric-Trace-Id\")}";

    private final ClassLoader targetClassLoader;
    private final String targetClassName;

    public HttpServletTransformer(Class<?> clazz) {
        targetClassLoader = clazz.getClassLoader();
        targetClassName = clazz.getName();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classFileBuffer) {
        String finalTargetClassName = targetClassName.replaceAll("\\.", "/");
        if (!className.equals(finalTargetClassName)) {
            return classFileBuffer;
        }

        if (!loader.equals(targetClassLoader)) {
            return classFileBuffer;
        }

        LOGGER.log(Level.INFO, "[Agent] Transforming class {0}", targetClassName);
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass timerClazz = classPool.get("ua.arlabunakty.core.model.TimerModel");
            CtClass clazz = classPool.get(targetClassName);

            // https://issues.redhat.com/browse/JASSIST-232?focusedCommentId=12993855&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-12993855
            // By setting the version number of Java 4, the creation of stack map table is suppressed.
            clazz.getClassFile()
                    .setMajorVersion(ClassFile.JAVA_4);
            CtMethod method = clazz.getDeclaredMethod(TRANSFORMED_METHOD_NAME);

            method.addLocalVariable("metricTimer", timerClazz);
            method.insertAfter("metricTimer.recordTimeInterval();", true);
            clazz.getClassFile()
                    .setMajorVersion(ClassFile.JAVA_7);

            method.insertBefore("metricTimer = " +
                    "ua.arlabunakty.core.service.ServiceFactory.getInstance()" +
                    "       .getStatsService()" +
                    "           .registerTimer(\"" + WebCategoryEnum.REQUEST_OPERATION_TIME.getId() + "\", " +
                    "                   " + GET_TRACKING_ID_HEADER_EXPRESSION + ");");

            method.insertBefore("$2 = " +
                    "new ua.arlabunakty.agent.transformer.servlet.http.ContentLengthTrackingResponseWrapper($2);");
            method.insertBefore(
                    "$2.addHeader(\"X-Metric-Trace-Id\", java.util.UUID.randomUUID().toString());");

            method.addLocalVariable("responseBodySize", CtClass.longType);

            String endBlock = "responseBodySize =" +
                    " ((ua.arlabunakty.agent.transformer.servlet.http.ContentLengthTrackingResponseWrapper) $2)" +
                    "       .getContentLength();" +
                    "ua.arlabunakty.core.service.ServiceFactory.getInstance()" +
                    "       .getStatsService()" +
                    "       .recordValue(responseBodySize, \"" + WebCategoryEnum.RESPONSE_BODY_LENGTH.getId() +
                    "\", " +
                    "           " + GET_TRACKING_ID_HEADER_EXPRESSION + ");";
            method.insertAfter(endBlock, true);

            byte[] byteCode = clazz.toBytecode();
            clazz.detach();
            return byteCode;
        } catch (NotFoundException | CannotCompileException | IOException e) {
            LOGGER.log(Level.WARNING, "Failed to transform class", e);
        }

        return classFileBuffer;
    }
}
