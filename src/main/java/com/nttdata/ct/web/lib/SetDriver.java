package com.nttdata.ct.web.lib;

import com.nttdata.ct.web.service.config.CheckProperties;
import com.nttdata.ct.web.service.config.PropertiesVault;
import com.nttdata.ct.web.service.config.options.BaseOptions;
import com.nttdata.ct.web.service.constans.Browser;
import com.nttdata.ct.web.service.constans.DriverProperties;
import com.nttdata.ct.web.service.util.UtilWeb;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Locale;
import java.util.logging.Level;

import static com.nttdata.ct.web.service.util.UtilWeb.logger;

@Component
public class SetDriver extends SimpleSetDriver {

    @Autowired
    protected PropertiesVault propertiesVault;
    @Autowired
    private BaseOptions options;

    private boolean isRemote;
    private boolean isCICDExecution;
    private String remoteHub;
    private String driverPath;
    private static final String DRIVER_LOCATED_ENVIRONMENT_PATH = "Driver located on Environment variable PATH";

    @PostConstruct
    public void initProperties() {
        isCICDExecution = propertiesVault.isCicdExecution();
        isRemote = propertiesVault.isWebDriverRemote();
        remoteHub = propertiesVault.getWebDriverHub();
        driverPath = propertiesVault.getDriverPath();
        logger(WebDriverManager.class).log(Level.INFO, "CI/CD Execution >>> {0}.", isCICDExecution);
        logger(WebDriverManager.class).log(Level.INFO, "RemoteExecution >>> {0}.", isRemote);
        if (isRemote)
            logger(WebDriverManager.class).log(Level.INFO, "Using remote nodes configured in hub {0}.", remoteHub);
        else
            logger(WebDriverManager.class).log(Level.INFO, "DriverPath >>> \"{0}\".", driverPath);
    }

    /**
     * Chrome driver configuration
     *
     * @return ChromeDriver
     */
    protected WebDriver configChromeDriver() {
        if (isSimpleDriver())
            return new ChromeDriver(getChromeOptions());
        if (isRemote)
            return setUpRemoteHub(options.getChrome().fetchChromeOptions(), remoteHub);
        if (!isCICDExecution)
            setWebDriverPropertyTo(Browser.CHROME);
        return new ChromeDriver(options.getChrome().fetchChromeOptions());
    }

    /**
     * Firefox driver configuration
     *
     * @return FirefoxDriver
     */
    protected WebDriver configFirefoxDriver() {
        if (isSimpleDriver())
            return new FirefoxDriver(getFirefoxOptions());
        if (isRemote)
            return setUpRemoteHub(options.getFirefox().fetchFirefoxOptions(), remoteHub);
        if (!isCICDExecution)
            setWebDriverPropertyTo(Browser.FIREFOX);
        return new FirefoxDriver(options.getFirefox().fetchFirefoxOptions());
    }

    /**
     * Safari driver configuration
     *
     * @return SafariDriver
     */
    protected WebDriver configSafariDriver() {
        if (isSimpleDriver())
            return new SafariDriver(getSafariOptions());
        else if (isRemote)
            return setUpRemoteHub(options.getSafari().fetchSafariOptions(), remoteHub);
        else
            return new SafariDriver(options.getSafari().fetchSafariOptions());
    }

    /**
     * Edge driver configuration
     *
     * @return EdgeDriver
     */
    protected WebDriver configEdgeDriver() {
        if (isSimpleDriver())
            return new EdgeDriver(getEdgeOptions());
        if (isRemote)
            return setUpRemoteHub(options.getEdge().fetchEdgeOptions(), remoteHub);
        if (!isCICDExecution)
            setWebDriverPropertyTo(Browser.EDGE);
        return new EdgeDriver(options.getEdge().fetchEdgeOptions());
    }

    private WebDriver setUpRemoteHub(MutableCapabilities capabilities, String driverHub) {
        final WebDriver driver;
        try {
            driver = new RemoteWebDriver(new URL(driverHub), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(propertiesVault.getImplicitWait()));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Error in \"setUpRemoteHub();\" >>>" + e.getMessage());
        }
        return driver;
    }

    private void setWebDriverPropertyTo(String browser) {
        if (isSimpleDriver())
            UtilWeb.logger(this.getClass())
                    .info("The driver was declared first.");
        else
            switch (browser.toUpperCase(Locale.ROOT)) {
                case Browser.CHROME:
                    chromeDriverPropertyPath();
                    break;
                case Browser.FIREFOX:
                    firefoxDriverropertyPath();
                    break;
                case Browser.EDGE:
                    edgeDriverropertyPath();
                    break;
                default:
                    throw new IllegalArgumentException("Browser name not supported '" + browser + "'");
            }
    }

    private void chromeDriverPropertyPath() {
        var driverPathChrome = propertiesVault.getDriverPathChrome();
        if (CheckProperties.isDefinided(driverPath) && !driverPath.isEmpty())
            System.setProperty(DriverProperties.CHROME_PROPERTY, driverPath);
        else if (CheckProperties.isDefinided(driverPathChrome) && !driverPathChrome.isEmpty())
            System.setProperty(DriverProperties.CHROME_PROPERTY, driverPathChrome);
        else
            UtilWeb.logger(this.getClass()).info(DRIVER_LOCATED_ENVIRONMENT_PATH);
    }

    private void firefoxDriverropertyPath() {
        var driverPathFirefox = propertiesVault.getDriverPathFirefox();
        if (CheckProperties.isDefinided(driverPath) && !driverPath.isEmpty())
            System.setProperty(DriverProperties.FIREFOX_PROPERTY, driverPath);
        else if (CheckProperties.isDefinided(driverPathFirefox) && !driverPathFirefox.isEmpty())
            System.setProperty(DriverProperties.FIREFOX_PROPERTY, driverPathFirefox);
        else
            UtilWeb.logger(this.getClass()).info(DRIVER_LOCATED_ENVIRONMENT_PATH);
        //Experimental
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
    }

    private void edgeDriverropertyPath() {
        var driverPathEdge = propertiesVault.getDriverPathEdge();
        if (CheckProperties.isDefinided(driverPath) && !driverPath.isEmpty())
            System.setProperty(DriverProperties.EDGE_PROPERTY, propertiesVault.getDriverPath());
        else if (CheckProperties.isDefinided(driverPathEdge) && !driverPathEdge.isEmpty())
            System.setProperty(DriverProperties.EDGE_PROPERTY, driverPathEdge);
        else
            UtilWeb.logger(this.getClass()).info(DRIVER_LOCATED_ENVIRONMENT_PATH);
    }

}