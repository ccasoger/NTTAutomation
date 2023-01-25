package com.nttdata.ct.web.base.dom;

import org.openqa.selenium.interactions.Actions;

public interface IWebBase {

    IWebDriverJavaScript js();

    IFindBy find() ;

    Actions actions();

    IShadowRoot sh();

}
