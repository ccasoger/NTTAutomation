package com.nttdata.ct.web.lib;

import org.openqa.selenium.WebDriver;

public interface CurrentDriver {

    static void goTo(String url) {
        WebDriverManager.getDriver().get(url);
    }

    static WebDriver getDriver() {
        return WebDriverManager.getDriver();
    }

}