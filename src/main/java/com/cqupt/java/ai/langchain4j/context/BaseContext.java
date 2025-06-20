package com.cqupt.java.ai.langchain4j.context;

/**
 *ThreadLocal
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static Long getCurrentUserId() {
        return threadLocal.get();
    }
    public static void setCurrentUserId(Long userId) {
        threadLocal.set(userId);
    }
    public static void removeCurrentUserId() {
        threadLocal.remove();
    }
}
