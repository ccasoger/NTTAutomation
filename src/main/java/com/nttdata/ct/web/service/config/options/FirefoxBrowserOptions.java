package com.nttdata.ct.web.service.config.options;

import com.nttdata.ct.web.lib.WebDriverManager;
import com.nttdata.ct.web.service.config.PropertiesVault;
import com.nttdata.ct.web.service.constans.Browser;
import com.nttdata.ct.web.service.io.ManageFiles;
import com.nttdata.ct.web.service.util.UtilWeb;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import static com.nttdata.ct.web.service.config.CheckProperties.isDefinided;

@Component
public class FirefoxBrowserOptions {

    @Autowired
    private PropertiesVault propertiesVault;

    private FirefoxOptions firefoxOptions;
    private String headlessDim;
    private boolean headless;
    private boolean acceptInsecureCerts;
    private List<String> firefoxExtraArgs;
    private String pageLoad;
    private boolean firefoxExtension;

    @PostConstruct
    private void init() {
        firefoxOptions = new FirefoxOptions();
        headless = propertiesVault.isWebDriverHeadless();
        acceptInsecureCerts = propertiesVault.isWebDriverAcceptInsecureCerts();
        headlessDim = propertiesVault.getWebDriverHeadlessDimension();
        firefoxExtraArgs = propertiesVault.getFirefoxExtraArgsOptions();
        pageLoad = propertiesVault.getPageLoadStrategy();
        firefoxExtension = propertiesVault.isWebDriverExtensionsOn();
    }

    public FirefoxOptions fetchFirefoxOptions() {
        firefoxOptions.setAcceptInsecureCerts(acceptInsecureCerts);
        firefoxOptions.setHeadless(headless);
        if (isDefinided(headlessDim))
            firefoxOptions.addArguments(headlessDim);
        setExtrasArgumentsOptions(firefoxOptions);
        setPageLoadStrategy();
        if (firefoxExtension)
            UtilWeb.logger(this.getClass()).log(Level.INFO, ManageFiles.readAsString("logs/log-browser-options.txt"),
                    new Object[]{"FirefoxOptions", firefoxOptions.getCapabilityNames()});
        else
            UtilWeb.logger(this.getClass()).log(Level.INFO, ManageFiles.readAsString("logs/log-browser-options.txt"),
                    new Object[]{"FirefoxOptions", firefoxOptions.asMap()});
        return firefoxOptions;
    }

    private void setPageLoadStrategy() {
        if (pageLoad.toUpperCase(Locale.ROOT).equals(Browser.PAGE_LOAD_STRATEGY_EAGER)) {
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        } else if (pageLoad.toUpperCase(Locale.ROOT).equals(Browser.PAGE_LOAD_STRATEGY_NONE)) {
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        } else {
            UtilWeb.logger(WebDriverManager.class).log(Level.INFO, "Normal page loader strategy.");
        }
    }

    private void setExtrasArgumentsOptions(FirefoxOptions firefoxOptions) {
        if (isDefinided(firefoxExtraArgs)) {
            UtilWeb.logger(WebDriverManager.class).log(Level.INFO, "FirefoxOptions extra arguments >>> True.");
            UtilWeb.logger(WebDriverManager.class).log(Level.INFO, "(+) Otros argumentos {0}", firefoxExtraArgs);
            for (String args : firefoxExtraArgs) {
                UtilWeb.logger(WebDriverManager.class).log(Level.INFO, "(+) Argument-FirefoxOptions {0}", args);
                firefoxOptions.addArguments(args.trim());
            }
        }
    }
}