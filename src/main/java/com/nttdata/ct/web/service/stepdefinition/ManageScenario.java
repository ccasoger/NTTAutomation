package com.nttdata.ct.web.service.stepdefinition;

import com.nttdata.ct.web.lib.WebDriverManager;
import com.nttdata.ct.web.service.io.ManageFiles;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.stereotype.Component;

@Component
public class ManageScenario {

    private static final ThreadLocal<Scenario> threadLocal = new ThreadLocal<>();

    public void removeThread(){
        threadLocal.remove();
    }

    public void setScenario(Scenario scenario) {
        threadLocal.set(scenario);
    }

    public Scenario getScenario() {
        return threadLocal.get();
    }

    public void printFullView() {
        if (getScenario() == null)
            throw new IllegalArgumentException(ManageFiles.readAsString("logs/log-no-scenario-declared.txt"));
        if (WebDriverManager.getDriver() == null)
            throw new IllegalArgumentException("WebDriver dosen't exist. Please, check the application properties file.");
        byte[] screenshot = ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        getScenario().attach(screenshot, "image/jpeg", "evidencia");
    }

    public void shotWhenFail() {
        if (getScenario().isFailed())
            printFullView();
    }

}