package com.nttdata.ct.web.base.dom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface IFindBy {

    WebElement getElementBy(By locator);

    List<WebElement> getElementsBy(By locator);

    WebElement getElementByXPath(String xpath);

    List<WebElement> getElementsByXPath(String xpath);

    WebElement getElementById(String id);

    List<WebElement> getElementsById(String id);

    WebElement getElementByCss(String css);

    List<WebElement> getElementsByCss(String css);

    List<WebElement> getElementsByElement(WebElement elementHost, By by);

    WebElement getElementByElement(WebElement elementHost, By by);

}
