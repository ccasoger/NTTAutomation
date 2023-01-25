package com.nttdata.ct.web.base.methods;

import com.nttdata.ct.web.base.dom.IWebDriverJavaScript;
import com.nttdata.ct.web.lib.WebDriverManager;
import com.nttdata.ct.web.service.util.UtilWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Level;

public class WebDriverJavaScriptDom implements IWebDriverJavaScript {

    private final WebDriver driver;

    private static final String NOT_FOUND_ELEMENT = "Elementos no encontrados.";

    public WebDriverJavaScriptDom() {
        this.driver = WebDriverManager.getDriver();
    }

    /**
     * Obtiene un elemento en shadowRoot por document.querySelector().shadowRoot.querySelector()
     *
     * @param cssQuerySelector Query XPath por CSS donde :
     *                         Cada contenedor de shadowroot esta separado por el simbolo ';'
     * @return el elemento encontrado
     */
    @Override
    public WebElement getWebElement(String cssQuerySelector) {
        JavascriptExecutor ex = (JavascriptExecutor) this.driver;
        String finalQuerySelector = getQuerySelectorShadowRoot(cssQuerySelector);
        WebElement element = (WebElement) ex.executeScript(finalQuerySelector);
        if (element == null) UtilWeb.logger(this.getClass()).log(Level.WARNING,
                "El elemento no fue encontrado.");
        return element;
    }

    /**
     * Obtiene el texto de un elemento en shadowRoot por posicion
     *
     * @param cssQuerySelector Query XPath por CSS donde :
     *                         Cada contenedor de shadowroot esta separado por el simbolo ';'
     * @return Texto del elemento
     */
    @Override
    public String getTextFromWebElement(String cssQuerySelector) {
        return getWebElement(cssQuerySelector).getAttribute("innerText");
    }

    /**
     * Obtiene una lista de elementos en shadowRoot por document.querySelector().shadowroot.querySelectorAll()
     *
     * @param cssQuerySelectorMerge Query XPath por CSS :
     *                              Cada contenedor de shadowroot esta separado por el simbolo ';',
     *                              El queryselector de querySelectorAll() esta separado por el simbolo '::'
     * @return lista de elementos
     */
    @Override
    public List<WebElement> getWebElements(String cssQuerySelectorMerge) {
        JavascriptExecutor ex = (JavascriptExecutor) this.driver;
        String[] querysStrings = cssQuerySelectorMerge.split("::");
        //obteniendo querySelector
        String cssQuerySelector = querysStrings[0];
        //obteniendo querySelectorAll
        String cssQuerySelectorAll = querysStrings[1];
        String finalQuerySelector = getQuerySelectorAllShadowRoot(cssQuerySelector, cssQuerySelectorAll);
        List<WebElement> elementList = (List<WebElement>) ex.executeScript(finalQuerySelector);
        if (elementList.isEmpty())
            UtilWeb.logger(this.getClass()).log(Level.WARNING, NOT_FOUND_ELEMENT);
        return elementList;
    }

    /**
     * Obtiene un elemento dentro de una lista en shadowRoot por posicion
     *
     * @param cssQuerySelectorMerge Query XPath por CSS :
     *                              Cada contenedor de shadowroot esta separado por el simbolo ';',
     *                              El queryselector de querySelectorAll() esta separado por el simbolo '::'
     * @param position              Posicion en la lista
     * @return el elemento encontrado por posicion
     */
    @Override
    public WebElement getWebElementByPosition(String cssQuerySelectorMerge, int position) {
        JavascriptExecutor ex = (JavascriptExecutor) this.driver;
        String[] querysStrings = cssQuerySelectorMerge.split("::");
        //obteniendo querySelector
        String cssQuerySelector = querysStrings[0];
        //obteniendo querySelectorAll
        String cssQuerySelectorAll = querysStrings[1];
        String finalQuerySelector = getQuerySelectorAllShadowRoot(cssQuerySelector, cssQuerySelectorAll);
        List<WebElement> elementList = (List<WebElement>) ex.executeScript(finalQuerySelector);
        if (elementList.isEmpty())
            UtilWeb.logger(this.getClass()).log(Level.WARNING, NOT_FOUND_ELEMENT);
        return elementList.get(position);
    }

    /**
     * Obtiene el texto de un elemento dentro de una lista en shadowRoot por posicion
     *
     * @param cssQuerySelectorMerge Query XPath por CSS :
     *                              Cada contenedor de shadowroot esta separado por el simbolo ';',
     *                              El queryselector de querySelectorAll() esta separado por el simbolo '::'
     * @param position              Posicion en la lista
     * @return Texto del elemento encontrado por posicion
     */
    @Override
    public String getTextFromListWebElementByPosition(String cssQuerySelectorMerge, int position) {
        JavascriptExecutor ex = (JavascriptExecutor) this.driver;
        String[] querysStrings = cssQuerySelectorMerge.split("::");
        //obteniendo querySelector
        String cssQuerySelector = querysStrings[0];
        //obteniendo querySelectorAll
        String cssQuerySelectorAll = querysStrings[1];
        String finalQuerySelector = getQuerySelectorAllShadowRoot(cssQuerySelector, cssQuerySelectorAll);
        List<WebElement> elementList = (List<WebElement>) ex.executeScript(finalQuerySelector);
        if (elementList.isEmpty())
            UtilWeb.logger(this.getClass()).log(Level.WARNING, NOT_FOUND_ELEMENT);
        return elementList.get(position).getAttribute("innerText");
    }

    /**
     * Genera querySelector para un elemento
     *
     * @param cssQuerySelector Query XPath por CSS donde :
     *                         Cada contenedor de shadowroot esta separado por el simbolo ';'
     * @return querySelector generado
     */
    @Override
    public String getQuerySelectorShadowRoot(String cssQuerySelector) {
        String[] shadowRootQuerySelector = cssQuerySelector.split(";");
        int ctd = shadowRootQuerySelector.length;
        String baseFirstRoot = shadowRootQuerySelector[0].trim();
        String finalQuerySelector = "return document.querySelector('%s')";
        finalQuerySelector = String.format(finalQuerySelector, baseFirstRoot);
        StringBuilder stringBuilder = new StringBuilder(finalQuerySelector);
        for (int i = 1; i < ctd; i++)
            stringBuilder.append(".shadowRoot.querySelector('").append(shadowRootQuerySelector[i].trim()).append("')");

        UtilWeb.logger(this.getClass()).log(Level.INFO, "Query selector >>> {0}", stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * Genera querySelector para una lista de elementos
     *
     * @param cssQuerySelector    Query XPath por CSS donde :
     *                            Cada contenedor de shadowroot esta separado por el simbolo ';'
     * @param cssQuerySelectorAll Ultimo xpath css enviado para ubicar la lista de elementos
     * @return querySelectorAll generado
     */
    @Override
    public String getQuerySelectorAllShadowRoot(String cssQuerySelector, String cssQuerySelectorAll) {
        String[] shadowRootQuerySelector = cssQuerySelector.split(";");
        int ctd = shadowRootQuerySelector.length;
        String baseFirstRoot = shadowRootQuerySelector[0];
        String finalQuerySelector = "return document.querySelector('%s')";
        finalQuerySelector = String.format(finalQuerySelector, baseFirstRoot);
        StringBuilder stringBuilder = new StringBuilder(finalQuerySelector);
        for (int i = 1; i < ctd; i++)
            stringBuilder.append(".shadowRoot.querySelector('").append(shadowRootQuerySelector[i]).append("')");
        stringBuilder.append(".shadowRoot.querySelectorAll('").append(cssQuerySelectorAll).append("')");
        UtilWeb.logger(this.getClass()).log(Level.INFO, "Query selector All--> {0}", stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * Realiza la accion de click sobre un elemento a traves de la clase JavascriptExecutor
     *
     * @param webElement elemento donde se realizara el comando.
     */
    @Override
    public void click(WebElement webElement) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", webElement);
    }

    /**
     * Obtiene el shadowRoot a partir del elemento contenedor 'webElementShadowHost'.
     *
     * @param webElementShadowHost Elemento contenedor del shadowRoot.
     * @return retorna el shadowRoot encontrado.
     */
    @Override
    public WebElement getShadowRoot(WebElement webElementShadowHost) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (WebElement) js.executeScript("return arguments[0].shadowRoot", webElementShadowHost);
    }

    /**
     * Realiza el scroll de un elemento hasta el top de la ventana.
     *
     * @param webElement elemento donde se realizara el comando.
     */
    @Override
    public void scrollElementTop(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView()", webElement);
    }

}