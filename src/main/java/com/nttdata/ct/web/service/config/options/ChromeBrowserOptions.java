package com.nttdata.ct.web.service.config.options;

import com.nttdata.ct.web.lib.WebDriverManager;
import com.nttdata.ct.web.service.config.CheckProperties;
import com.nttdata.ct.web.service.config.PropertiesVault;
import com.nttdata.ct.web.service.constans.Browser;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import static com.nttdata.ct.web.service.config.CheckProperties.isDefinided;
import static com.nttdata.ct.web.service.io.ManageFiles.readAsString;
import static com.nttdata.ct.web.service.util.UtilWeb.logger;

@Component
public class ChromeBrowserOptions {

    @Autowired
    private PropertiesVault propertiesVault;

    private ChromeOptions chromeOptions;
    private boolean headless;
    private String headlessDim;
    private boolean acceptInsecureCerts;
    private String windowsSize;
    private String pageLoad;
    private String chromeDeviceResponsive;
    private int chromeDeviceHeight;
    private int chromeDeviceWidth;
    private double chromeDevicePixelRatio;
    private boolean chromeDeviceTouch;
    private List<String> chromeExtraArgs;

    private boolean chromeExtension;
    private String chromeExtensionName;
    private String chromeExtensionPathCRX;

    @PostConstruct
    private void init() {
        chromeOptions = new ChromeOptions();
        headless = propertiesVault.isWebDriverHeadless();
        headlessDim = propertiesVault.getWebDriverHeadlessDimension();
        acceptInsecureCerts = propertiesVault.isWebDriverAcceptInsecureCerts();
        windowsSize = propertiesVault.getWebDriverSize();
        chromeDeviceResponsive = propertiesVault.getChromeDeviceResponsive();
        chromeDeviceHeight = propertiesVault.getChromeDeviceHeight();
        chromeDeviceWidth = propertiesVault.getChromeDeviceWidth();
        chromeDevicePixelRatio = propertiesVault.getChromeDevicePixelRatio();
        chromeDeviceTouch = propertiesVault.isChromeDeviceTouch();
        chromeExtraArgs = propertiesVault.getChromeExtraArgsOptions();
        pageLoad = propertiesVault.getPageLoadStrategy();
        //Extensions
        chromeExtension = propertiesVault.isWebDriverExtensionsOn();
        chromeExtensionName = propertiesVault.getWebDriverExtensionName();
        chromeExtensionPathCRX = propertiesVault.getWebDriverExtensionPathCRX();
    }

    public ChromeOptions fetchChromeOptions() {
        chromeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
        if (chromeExtension) {
            logger(WebDriverManager.class).log(Level.INFO, "Adding extension >>> {0}", chromeExtensionName);
            chromeOptions.addExtensions(new File(chromeExtensionPathCRX));
        }
        chromeOptions.setHeadless(headless);
        if (isDefinided(headlessDim))
            chromeOptions.addArguments(headlessDim);
        if (windowsSize.equals(Browser.WIN_SIZE_FULLSCREEN))
            chromeOptions.addArguments("--kiosk");
        setExtrasArgumentsOptions(chromeOptions);
        setPageLoadStrategy();
        setDeviceResponsive();
        setDeviceCustomResponsive();
        if (chromeExtension)
            logger(this.getClass()).log(Level.INFO, readAsString("logs/log-browser-options.txt"),
                    new Object[]{"ChromeOptions", chromeOptions.getCapabilityNames()});
        else
            logger(this.getClass()).log(Level.INFO, readAsString("logs/log-browser-options.txt"),
                    new Object[]{"ChromeOptions", chromeOptions.asMap()});
        return chromeOptions;
    }

    private void setExtrasArgumentsOptions(ChromeOptions chromeOptions) {
        if (isDefinided(chromeExtraArgs)) {
            logger(WebDriverManager.class).log(Level.INFO, "ChromeOptions extra arguments >>> True.");
            logger(WebDriverManager.class).log(Level.INFO, "(+) Otros argumentos {0}", chromeExtraArgs);
            for (String args : chromeExtraArgs) {
                logger(WebDriverManager.class).log(Level.INFO, "(+) Argument-ChromeOption {0}", args);
                chromeOptions.addArguments(args.trim());
            }
        }
    }

    private void setPageLoadStrategy() {
        logger(WebDriverManager.class).log(Level.INFO, "Load Strategy >>> {0}.", pageLoad);
        if (pageLoad.toUpperCase(Locale.ROOT).equals(Browser.PAGE_LOAD_STRATEGY_EAGER)) {
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        } else if (pageLoad.toUpperCase(Locale.ROOT).equals(Browser.PAGE_LOAD_STRATEGY_NONE)) {
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        } else {
            logger(WebDriverManager.class).log(Level.INFO, "Normal page loader strategy.");
        }
    }

    private void setDeviceResponsive() {
        var deviceName = "deviceName";
        var mobileEmulation = "mobileEmulation";
        if (CheckProperties.isDefinided(chromeDeviceResponsive)) {
            logger(WebDriverManager.class).log(Level.INFO, "Execution on responsive >>> {0}", chromeDeviceResponsive);
            Map<String, String> mobileEmulationMap = new HashMap<>();
            mobileEmulationMap.put(deviceName, chromeDeviceResponsive);
            chromeOptions.setExperimentalOption(mobileEmulation, mobileEmulationMap);
        }
    }

    private void setDeviceCustomResponsive() {
        var mobileEmulation = "mobileEmulation";
        var deviceMetrics = "deviceMetrics";
        var deviceWidth = "width";
        var deviceHeight = "height";
        var devicepPixelRatio = "pixelRatio";
        var deviceTouch = "touch";
        if (CheckProperties.isDefinided(chromeDeviceHeight) && CheckProperties.isDefinided(chromeDeviceWidth)) {
            logger(WebDriverManager.class).log(Level.INFO, readAsString("logs/log-chrome-responsive-custom-device.txt"),
                    new Object[]{chromeDeviceWidth, chromeDeviceHeight, chromeDevicePixelRatio, chromeDeviceTouch});
            Map<String, Object> deviceMetricsMap = new HashMap<>();
            deviceMetricsMap.put(deviceWidth, chromeDeviceWidth);
            deviceMetricsMap.put(deviceHeight, chromeDeviceHeight);
            deviceMetricsMap.put(devicepPixelRatio, chromeDevicePixelRatio);
            deviceMetricsMap.put(deviceTouch, chromeDeviceTouch);
            Map<String, Object> mobileEmulationMap = new HashMap<>();
            mobileEmulationMap.put(deviceMetrics, deviceMetricsMap);
            mobileEmulationMap.put("userAgent",
                    "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
            chromeOptions.setExperimentalOption(mobileEmulation, mobileEmulationMap);
        }
    }
}