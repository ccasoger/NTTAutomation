package com.nttdata.ct.web.lib;

import com.nttdata.ct.web.service.config.CheckProperties;
import com.nttdata.ct.web.service.constans.Browser;
import com.nttdata.ct.web.service.io.ManageFiles;
import com.nttdata.ct.web.service.util.UtilWeb;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Locale;
import java.util.logging.Level;

@Component
public class WebDriverManager extends SetDriver implements IWebDriverManager {

    private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();

    private void setDriver(WebDriver driver) {
        threadLocal.set(driver);
    }

    private void removeThread() {
        threadLocal.remove();
    }

    /**
     * Obtiene el Driver que se esta ejecutando en ese momento copiado por la clase ThreadLocal
     *
     * @return retonra el Driver ejecutandose en un hilo especifico administrado por la clase ThreadLocal.
     */
    public static WebDriver getDriver() {
        return threadLocal.get();
    }

    /**
     * Metodo que crea la intancia del nuevo Driver a partir de un browser soportado.
     * Los browser soportados para este metodo son: chrome | safari | firefox | edge
     */
    @Override
    public void setUpDriver() {
        var browser = propertiesVault.getBrowser();
        var windowsSize = propertiesVault.getWebDriverSize();
        var implicitWait = propertiesVault.getImplicitWait();
        var urlBase = propertiesVault.getUrlBase();
        setUp(browser, windowsSize, implicitWait, urlBase);
    }

    /**
     * Metodo que crea la intancia del nuevo Driver a partir de un browser soportado.
     * Enviando los datos predefinidos.
     * Los browser soportados para este metodo son: chrome | safari | firefox | edge
     */
    @Override
    public void setUpDriver(String browser, String windowsSize, int implicitWaitOnSeconds, String urlBase,
                            MutableCapabilities options) {
        setSimpleDriver(true);
        switch (browser.toUpperCase(Locale.ROOT)) {
            case Browser.CHROME:
                setChromeOptions((ChromeOptions) options);
                break;
            case Browser.FIREFOX:
                setFirefoxOptions((FirefoxOptions) options);
                break;
            case Browser.SAFARI:
                setSafariOptions((SafariOptions) options);
                break;
            case Browser.EDGE:
                setEdgeOptions((EdgeOptions) options);
                break;
            default:
                UtilWeb.logger(this.getClass()).log(Level.WARNING,
                        ManageFiles.readAsString("logs/log-not-supported-browser.txt"), browser);
                throw new IllegalArgumentException();
        }
        setUp(browser, windowsSize, implicitWaitOnSeconds, urlBase);
    }

    private void setUp(String browser, String windowsSize, int implicitWaitOnSeconds, String urlBase) {
        UtilWeb.logger(this.getClass()).log(Level.INFO, "BrowserName >>> {0}.", browser);
        UtilWeb.logger(this.getClass()).log(Level.INFO, "ImplicitWait >>> {0} seconds.", implicitWaitOnSeconds);
        UtilWeb.logger(this.getClass()).log(Level.INFO, "Go to URL >>> {0} seconds.", urlBase);
        if (CheckProperties.isDefinided(browser) && !browser.isEmpty()) {
            WebDriver driver;
            switch (browser.toUpperCase(Locale.ROOT)) {
                case Browser.CHROME:
                    driver = configChromeDriver();
                    break;
                case Browser.FIREFOX:
                    driver = configFirefoxDriver();
                    break;
                case Browser.SAFARI:
                    driver = configSafariDriver();
                    break;
                case Browser.EDGE:
                    driver = configEdgeDriver();
                    break;
                default:
                    UtilWeb.logger(this.getClass()).log(Level.WARNING,
                            ManageFiles.readAsString("logs/log-not-supported-browser.txt"), browser);
                    throw new IllegalArgumentException();
            }
            setDriver(driver);
            configWindowsSize(windowsSize);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitOnSeconds));
            if (CheckProperties.isDefinided(urlBase))
                navigateTo(urlBase);
        } else {
            UtilWeb.logger(this.getClass()).warning("Property \"webdriver.browser\" is not defined in file \"application.properties\".");
            throw new IllegalArgumentException();
        }
    }

    private void configWindowsSize(String windowsSize) {
        switch (windowsSize.toUpperCase(Locale.ROOT)) {
            case "":
            case Browser.WIN_SIZE_MAXIMIZE:
                maximize();
                break;
            case Browser.WIN_SIZE_FULLSCREEN:
                fullScreen();
                break;
            case Browser.WIN_SIZE_NONE:
            default:
                UtilWeb.logger(this.getClass()).log(Level.INFO, ManageFiles.readAsString("logs/log-browser-windows-size.txt"),
                        windowsSize);
                break;
        }
    }

    @Override
    public void navigateTo(String url) {
        UtilWeb.logger(this.getClass()).log(Level.INFO, "Navigating to website >>> \"{0}\", on Thread - \"{1}, {2}\"",
                new Object[]{url, Thread.currentThread().getName(), Thread.currentThread().getId()});
        getDriver().navigate().to(url);
    }

    /**
     * Maximiza la dimension de la venta del browser.
     */
    @Override
    public void maximize() {
        UtilWeb.logger(this.getClass()).info("Maximizing website.");
        getDriver().manage().window().maximize();
    }

    /**
     * Amplia las dimensiones de la ventana a Pantalla completa del browser.
     */
    @Override
    public void fullScreen() {
        UtilWeb.logger(this.getClass()).info("FullScreen website.");
        getDriver().manage().window().fullscreen();
    }

    /**
     * Detiene el Driver del browser ejecutado.
     */
    @Override
    public void quitDriver() {
        if (isDriverOn())
            getDriver().quit();
        else
            UtilWeb.logger(this.getClass()).warning("Driver session does not exist.");
    }

    /**
     * Valida si actualmente existe algun Driver ejectuandose
     *
     * @return retorna verdadero o falso dependiendo del estado del Driver.
     */
    @Override
    public boolean isDriverOn() {
        return getDriver() != null;
    }

}