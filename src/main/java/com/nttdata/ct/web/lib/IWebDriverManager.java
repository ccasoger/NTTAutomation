package com.nttdata.ct.web.lib;

import org.openqa.selenium.MutableCapabilities;

public interface IWebDriverManager {

    void setUpDriver();

    void setUpDriver(String browser, String size, int implicitWaitOnSeconds, String url, MutableCapabilities capabilities);

    void navigateTo(String url);

    void maximize();

    void fullScreen();

    void quitDriver();

    boolean isDriverOn();
}
