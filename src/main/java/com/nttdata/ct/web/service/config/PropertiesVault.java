package com.nttdata.ct.web.service.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PropertiesVault {

    @Value("${webdriver.implicitWaitOnSeconds:30}")
    private int implicitWait;
    @Value("${webdriver.cicd:false}")
    private boolean cicdExecution;

    @Value("${webdriver.path:NOT_DEFINED}")
    private String driverPath;
    @Value("${webdriver.path.chrome:NOT_DEFINED}")
    private String driverPathChrome;
    @Value("${webdriver.path.firefox:NOT_DEFINED}")
    private String driverPathFirefox;
    @Value("${webdriver.path.edge:NOT_DEFINED}")
    private String driverPathEdge;

    @Value("${webdriver.browser:NOT_DEFINED}")
    private String browser;
    @Value("${webdriver.urlBase:NOT_DEFINED}")
    private String urlBase;
    @Value("${webdriver.size:MAXIMIZE}")
    private String webDriverSize;
    @Value("${webdriver.pageload:NORMAL}")
    private String pageLoadStrategy;

    @Value("${webdriver.headless:false}")
    private boolean webDriverHeadless;
    @Value("${webdriver.headless.dimension:NOT_DEFINED}")
    private String webDriverHeadlessDimension;
    @Value("${webdriver.accept.insecure.certs:false}")
    private boolean webDriverAcceptInsecureCerts;

    @Value("${webdriver.remote:false}")
    private boolean webDriverRemote;
    @Value("${webdriver.hub:NOT_DEFINED}")
    private String webDriverHub;

    @Value("${webdriver.extensions:false}")
    private boolean isWebDriverExtensionsOn;
    @Value("${webdriver.extension.name:NOT_DEFINED}")
    private String webDriverExtensionName;
    @Value("${webdriver.extension.pathCRX:NOT_DEFINED}")
    private String webDriverExtensionPathCRX;

    //Chrome Properties
    @Value("${webdriver.chrome.device:NOT_DEFINED}")
    private String chromeDeviceResponsive;
    @Value("${webdriver.chrome.device.width:0}")
    private int chromeDeviceWidth;
    @Value("${webdriver.chrome.device.height:0}")
    private int chromeDeviceHeight;
    @Value("${webdriver.chrome.device.pixelRatio:0.3}")
    private double chromeDevicePixelRatio;
    @Value("${webdriver.chrome.device.touch:true}")
    private boolean chromeDeviceTouch;
    @Value("${webdriver.chrome.extraArgs:}")
    private List<String> chromeExtraArgsOptions;

    //Firefox Properties
    @Value("${webdriver.firefox.extraArgs:}")
    private List<String> firefoxExtraArgsOptions;
    @Value("${webdriver.extension.firefox.pathXPI:NOT_DEFINED}")
    private String firefoxPathXpi;

    //Edge Properties
    @Value("${webdriver.edge.extraArgs:}")
    private List<String> edgeExtraArgsOptions;

    //Safari Properties

}