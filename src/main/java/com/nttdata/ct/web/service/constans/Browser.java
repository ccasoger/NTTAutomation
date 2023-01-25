package com.nttdata.ct.web.service.constans;

public class Browser {

    private Browser(){
        throw new IllegalStateException("Utility class");
    }

    public static final String CHROME = "CHROME";
    public static final String FIREFOX = "FIREFOX";
    public static final String SAFARI = "SAFARI";
    public static final String EDGE = "EDGE";

    public static final String WIN_SIZE_MAXIMIZE = "MAXIMIZE";
    public static final String WIN_SIZE_FULLSCREEN = "FULLSCREEN";
    public static final String WIN_SIZE_NONE = "NONE";

    public static final String PAGE_LOAD_STRATEGY_EAGER = "EAGER";
    public static final String PAGE_LOAD_STRATEGY_NONE = "NONE";

}
