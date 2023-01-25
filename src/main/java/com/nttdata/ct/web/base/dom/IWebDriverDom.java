package com.nttdata.ct.web.base.dom;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;
import java.util.List;

public interface IWebDriverDom {

    void click(WebElement webElement);

    void click(WebElement webElement, int timeOutOnSeconds);

    void type(WebElement webElement, String charSequences);

    void type(WebElement webElement, String charSequences, int timeOutOnSeconds);

    String getText(WebElement webElement);

    String getText(WebElement webElement, int timeOutOnSeconds);

    String getAttribute(WebElement webElement, String attributeName);

    String getAttribute(WebElement webElement, String attributeName, int timeOutOnSeconds);

    void clickElementInAList(List<WebElement> listElement, String value);

    void clickElementInAList(List<WebElement> listElement, String value, int timeOutOnSeconds);

    boolean isEnabled(WebElement webElement);

    boolean isEnabled(WebElement webElement, int timeOutOnSeconds);

    boolean isDisplayed(WebElement webElement);

    boolean isDisplayed(WebElement webElement, int timeOutOnSeconds);

    boolean isSelected(WebElement webElement);

    boolean isSelected(WebElement webElement, int timeOutOnSeconds);

    WebElement waitUntilElementIsVisible(WebElement webElement, int timeOutInSeconds);

    WebElement waitUntilElementIsClickable(WebElement webElement, int timeOutInSeconds);

    WebElement waitUntilElement(ExpectedCondition<WebElement> expectedCondition, Duration duration);

    List<WebElement> waitUntilElements(ExpectedCondition<List<WebElement>> expectedCondition, Duration duration);

    Alert waitUntilAlertIsPresent(Duration duration);

}
