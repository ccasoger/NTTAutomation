package com.nttdata.ct.web.base.methods;

import com.nttdata.ct.web.base.dom.IFindBy;
import com.nttdata.ct.web.lib.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FindByDom implements IFindBy {

    private final WebDriver driver;

    public FindByDom() {
        this.driver = WebDriverManager.getDriver();
    }

    /**
     * Encuentra el elemento a partir de la clase locator de selenium By.
     *
     * @param locator Localizador del elemento en base a la clase de Selenium By.
     *                Por ejemplo: By.id("id-del-elemento")
     * @return retorna el elemento encontrado.
     */
    @Override
    public WebElement getElementBy(By locator) {
        return this.driver.findElement(locator);
    }

    /**
     * Encuentra una lista de elementos a partir de la clase locator de Selenium By.
     *
     * @param locator Localizador del elemento en base a la clase de Selenium By.
     *                Por ejemplo: By.id("id-del-elemento")
     * @return retorna la lista de elementos encontrados.
     */
    @Override
    public List<WebElement> getElementsBy(By locator) {
        return this.driver.findElements(locator);
    }

    /**
     * Encuentra al elemento a partir del localizar XPATH construido, de tipo de dato String.
     *
     * @param xpath Localizar del elemento construido de tipo String.
     * @return retorna el elemento encontrado.
     */
    @Override
    public WebElement getElementByXPath(String xpath) {
        return this.driver.findElement(By.xpath(xpath));
    }

    /**
     * Encuentra una lista de elementos a partir del localizar XPATH construido, de tipo de dato String.
     *
     * @param xpath Localizar del elemento construido de tipo String.
     * @return retorna la lista de elementos encontrados.
     */
    @Override
    public List<WebElement> getElementsByXPath(String xpath) {
        return this.driver.findElements(By.xpath(xpath));
    }

    /**
     * Encuentra al elemento a partir del localizar ID, de tipo de dato String.
     *
     * @param id Localizador ID del elemento, de tipo de dato String.
     * @return retorna el elemento encontrado.
     */
    @Override
    public WebElement getElementById(String id) {
        return this.driver.findElement(By.id(id));
    }

    /**
     * Encuentra una lista de elementos a partir del localizar ID, de tipo de dato String.
     *
     * @param id Localizador ID del elemento, de tipo de dato String.
     * @return retorna la lista de elementos encontrados.
     */
    @Override
    public List<WebElement> getElementsById(String id) {
        return this.driver.findElements(By.id(id));
    }

    /**
     * Encuentra al elemento a partir del localizar CSS selector, de tipo de dato String.
     *
     * @param css Localizador CSS selector del elemento, de tipo de dato String.
     * @return retorna el elemento encontrado.
     */
    @Override
    public WebElement getElementByCss(String css) {
        return this.driver.findElement(By.cssSelector(css));
    }

    /**
     * Encuentra una lista de elementos a partir del localizar CSS selector, de tipo de dato String.
     *
     * @param css Localizador CSS selector del elemento, de tipo de dato String.
     * @return retorna la lista de elementos encontrados.
     */
    @Override
    public List<WebElement> getElementsByCss(String css) {
        return this.driver.findElements(By.cssSelector(css));
    }

    /**
     * Encuentra a un elemento 'child' dentro de otro elemento 'host' a partir de la clase locator de selenium By.
     *
     * @param elementHost Elemento contenedor
     * @param locator     Localizador del elemento en base a la clase de Selenium By.
     *                    Por ejemplo: By.id("id-del-elemento")
     * @return retorna el elemento encontrado.
     */
    @Override
    public WebElement getElementByElement(WebElement elementHost, By locator) {
        return elementHost.findElement(locator);
    }

    /**
     * Encuentra una lista de elementos 'child' dentro de otro elemento 'host' a partir de la clase locator de selenium By.
     *
     * @param elementHost Elemento contenedor
     * @param locator     Localizador del elemento en base a la clase de Selenium By.
     *                    Por ejemplo: By.id("id-del-elemento")
     * @return retorna la lista de elementos encontrados.
     */
    @Override
    public List<WebElement> getElementsByElement(WebElement elementHost, By locator) {
        return elementHost.findElements(locator);
    }

}