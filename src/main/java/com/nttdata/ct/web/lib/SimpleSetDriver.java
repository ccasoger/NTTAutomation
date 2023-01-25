package com.nttdata.ct.web.lib;

import com.nttdata.ct.web.service.util.UtilWeb;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Objects;
import java.util.logging.Level;

public class SimpleSetDriver {

    protected SimpleSetDriver() {
        //Sonar rule
    }

    protected static boolean simpleDriver;
    protected static ChromeOptions chromeOptions;
    protected static SafariOptions safariOptions;
    protected static FirefoxOptions firefoxOptions;
    protected static EdgeOptions edgeOptions;

    public static SafariOptions getSafariOptions() {
        return safariOptions;
    }

    public static void setSafariOptions(SafariOptions safariOptions) {
        SimpleSetDriver.safariOptions = Objects.requireNonNullElseGet(safariOptions, SafariOptions::new);
    }

    public static FirefoxOptions getFirefoxOptions() {
        return firefoxOptions;
    }

    public static void setFirefoxOptions(FirefoxOptions firefoxOptions) {
        SimpleSetDriver.firefoxOptions = Objects.requireNonNullElseGet(firefoxOptions, FirefoxOptions::new);
    }

    public static EdgeOptions getEdgeOptions() {
        return edgeOptions;
    }

    public static void setEdgeOptions(EdgeOptions edgeOptions) {
        SimpleSetDriver.edgeOptions = Objects.requireNonNullElseGet(edgeOptions, EdgeOptions::new);
    }

    protected static ChromeOptions getChromeOptions() {
        return chromeOptions;
    }

    protected static void setChromeOptions(ChromeOptions chromeOptions) {
        SimpleSetDriver.chromeOptions = Objects.requireNonNullElseGet(chromeOptions, ChromeOptions::new);
        UtilWeb.logger(SimpleSetDriver.class).log(Level.INFO, "Options >>> {0}", SimpleSetDriver.chromeOptions);
    }

    public static boolean isSimpleDriver() {
        return simpleDriver;
    }

    public static void setSimpleDriver(boolean simpleDriver) {
        SimpleSetDriver.simpleDriver = simpleDriver;
    }
}
