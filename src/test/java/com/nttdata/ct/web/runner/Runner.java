package com.nttdata.ct.web.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"json:target/build/cucumber.json"},
        publish = false,
        stepNotifications = true,
        features = {"src/test/resources/features"},
        glue = {"com.nttdata.ct.web.hooks", "com.nttdata.ct.web.glue"},
        tags = "@TAG"
)
public class Runner {

    @BeforeClass
    public static void beforeExecution() {
        Logger.getLogger(Runner.class.getName()).log(Level.INFO, "BEFORE EXECUTION --->");
    }

    @AfterClass
    public static void afterExecution() {
        Logger.getLogger(Runner.class.getName()).log(Level.INFO, "AFTER EXECUTION --->");
    }

}