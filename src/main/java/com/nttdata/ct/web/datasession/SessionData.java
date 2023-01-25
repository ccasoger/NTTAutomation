package com.nttdata.ct.web.datasession;

import com.nttdata.ct.web.service.util.UtilWeb;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class SessionData {

    private SessionData() {
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadLocal<Map<Object, Object>> sessionThreadLocal = new ThreadLocal<>();
    private static final Map<Object, Object> newMap = new LinkedHashMap<>();

    public void removeThread() {
        sessionThreadLocal.remove();
    }

    public static void setSessionVariable(Object key, Object value) {
        newMap.put(key, value);
        sessionThreadLocal.set(newMap);
    }

    private static Map<Object, Object> getCurrentSession() {
        return sessionThreadLocal.get();
    }

    public static <T> T getVariableCalled(String key) {
        return (T) getCurrentSession().get(key);
    }

    public static void printCurrentSession() {
        UtilWeb.logger(SessionData.class).log(Level.INFO, "Currently saved keys >>>" +
                "\n* Keys/Values: {0}," +
                "\n* Thread: {1}", new Object[]{getCurrentSession(),
                Thread.currentThread().getId() + " - " + Thread.currentThread().getName()});
    }

    public static Map<Object, Object> getCurrentSessionValues() {
        return getCurrentSession();
    }

    public static int getCurrentSessionSize() {
        return getCurrentSession().size();
    }


    public static void clear() {
        getCurrentSession().clear();
    }

    public static boolean containsKey(String key) {
        return getCurrentSession().containsKey(key);
    }

    public static boolean containsValue(Object value) {
        return getCurrentSession().containsValue(value);
    }

}
