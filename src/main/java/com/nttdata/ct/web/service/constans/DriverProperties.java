package com.nttdata.ct.web.service.constans;

public class DriverProperties {

    private DriverProperties() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CHROME_PROPERTY = "webdriver.chrome.driver";
    public static final String FIREFOX_PROPERTY = "webdriver.gecko.driver";
    public static final String EDGE_PROPERTY = "webdriver.edge.driver";

}
