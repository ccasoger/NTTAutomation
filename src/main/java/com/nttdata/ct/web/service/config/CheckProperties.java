package com.nttdata.ct.web.service.config;

import com.nttdata.ct.web.service.constans.Constants;

import java.util.List;

public final class CheckProperties {

    private CheckProperties() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isDefinided(String property) {
        return !property.equals(Constants.NOT_DEFINED);
    }

    public static boolean isDefinided(int property) {
        return property > 0;
    }

    public static boolean isDefinided(List<String> propertiesList) {
        return !propertiesList.isEmpty();
    }

    public static boolean isNotDefinided(String property) {
        return property.equals(Constants.NOT_DEFINED);
    }

}