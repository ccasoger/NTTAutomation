package com.nttdata.ct.web.service.config.options;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BaseOptions {

    @Autowired
    private ChromeBrowserOptions chrome;
    @Autowired
    private FirefoxBrowserOptions firefox;
    @Autowired
    private SafariBrowserOptions safari;
    @Autowired
    private EdgeBrowserOptions edge;

}
