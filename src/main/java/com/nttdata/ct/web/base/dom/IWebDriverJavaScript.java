package com.nttdata.ct.web.base.dom;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface IWebDriverJavaScript {

    WebElement getWebElement(String cssQuerySelector);

    String getTextFromWebElement(String cssQuerySelector);

    List<WebElement> getWebElements(String cssQuerySelectorMerge);

    WebElement getWebElementByPosition(String cssQuerySelectorMerge, int position);

    String getTextFromListWebElementByPosition(String cssQuerySelectorMerge, int position);

    String getQuerySelectorShadowRoot(String cssQuerySelector);

    String getQuerySelectorAllShadowRoot(String cssQuerySelector, String cssQuerySelectorAll);

    void click(WebElement webElement);

    WebElement getShadowRoot(WebElement shadowHost);

    public void scrollElementTop(WebElement webElement);

}
