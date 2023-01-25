package com.nttdata.ct.web.service.config.options;

import com.nttdata.ct.web.service.config.PropertiesVault;
import com.nttdata.ct.web.service.io.ManageFiles;
import com.nttdata.ct.web.service.util.UtilWeb;
import org.openqa.selenium.safari.SafariOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;

@Component
public class SafariBrowserOptions {

    @Autowired
    private PropertiesVault propertiesVault;

    private SafariOptions safariOptions;
    private boolean acceptInsecureCerts;

    @PostConstruct
    private void init() {
        safariOptions = new SafariOptions();
        acceptInsecureCerts = propertiesVault.isWebDriverAcceptInsecureCerts();
    }

    public SafariOptions fetchSafariOptions() {
        safariOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
        UtilWeb.logger(this.getClass()).log(Level.INFO, ManageFiles.readAsString("logs/log-browser-options.txt"),
                new Object[]{"SafariOptions", safariOptions.asMap()});
        return safariOptions;
    }

}