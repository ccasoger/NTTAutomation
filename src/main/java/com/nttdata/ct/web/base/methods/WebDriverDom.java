package com.nttdata.ct.web.base.methods;

import com.nttdata.ct.web.base.dom.IWebDriverDom;
import com.nttdata.ct.web.lib.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebDriverDom implements IWebDriverDom {

    private final WebDriver driver;

    public WebDriverDom() {
        this.driver = WebDriverManager.getDriver();
    }

    /**
     * Metodo de la interface de Selenium que, realiza el comando click.
     *
     * @param webElement elemento donde se realizara el comando.
     */
    @Override
    public void click(WebElement webElement) {
        webElement.click();
    }

    /**
     * Metodo de la interface de Selenium que, realiza el comando click con un tiempo de espera.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     */
    @Override
    public void click(WebElement webElement, int timeOutOnSeconds) {
        waitUntilElementIsClickable(webElement, timeOutOnSeconds).click();
    }

    /**
     * Metodo de la interface de Selenium que, tipea un cojunto de caracteres.
     *
     * @param charSequences conjunto de caracteres que se tipearan en el elemento
     * @param webElement    elemento donde se realizara el comando.
     */
    @Override
    public void type(WebElement webElement, String charSequences) {
        webElement.sendKeys(charSequences);
    }

    /**
     * Metodo de la interface de Selenium que, tipea un cojunto de caracteres con un tiempo de espera.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param charSequences    conjunto de caracteres que se tipearan en el elemento
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     */
    @Override
    public void type(WebElement webElement, String charSequences, int timeOutOnSeconds) {
        waitUntilElementIsVisible(webElement, timeOutOnSeconds).sendKeys(charSequences);
    }

    /**
     * Metodo de la interface de Selenium que, obtiene el texto del elemento.
     *
     * @param webElement elemento donde se realizara el comando.
     */
    @Override
    public String getText(WebElement webElement) {
        return webElement.getText();
    }

    /**
     * Metodo de la interface de Selenium que, obtiene el texto del elemento con un tiempo de espera.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     */
    @Override
    public String getText(WebElement webElement, int timeOutOnSeconds) {
        return waitUntilElementIsVisible(webElement, timeOutOnSeconds).getText();
    }

    /**
     * Metodo de la interface de Selenium que, obtiene el valor del atributo de un elemento.
     *
     * @param webElement    elemento donde se realizara el comando.
     * @param attributeName nombre del atributo del elemento.
     */
    @Override
    public String getAttribute(WebElement webElement, String attributeName) {
        return webElement.getAttribute(attributeName);
    }

    /**
     * Metodo de la interface de Selenium que, obtiene el valor del atributo de un elemento con un tiempo de espera.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     */
    @Override
    public String getAttribute(WebElement webElement, String attributeName, int timeOutOnSeconds) {
        return waitUntilElementIsVisible(webElement, timeOutOnSeconds).getAttribute(attributeName);
    }

    /**
     * Metodo que ejecuta el comando click sobre un elemento dentro de la lista de elementos por el texto.
     *
     * @param listElement Lista de elementos.
     * @param value       Texto del elemento por el cual se quiere localizar
     */
    @Override
    public void clickElementInAList(List<WebElement> listElement, String value) {
        for (WebElement webElement : listElement) {
            if (webElement.getText().equalsIgnoreCase(value)) {
                webElement.click();
                break;
            }
        }
    }

    /**
     * Metodo que ejecuta el comando click sobre un elemento dentro de la lista de elementos por el texto
     * con un tiempo de espera.
     *
     * @param listElement      Lista de elementos.
     * @param value            Texto del elemento por el cual se quiere localizar
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     */
    @Override
    public void clickElementInAList(List<WebElement> listElement, String value, int timeOutOnSeconds) {
        for (WebElement webElement : listElement) {
            if (getText(webElement, timeOutOnSeconds).equalsIgnoreCase(value)) {
                click(webElement, timeOutOnSeconds);
                break;
            }
        }
    }

    /**
     * Metodo que devuelve si el elemento esta habilitado o no.
     *
     * @param webElement elemento donde se realizara el comando.
     * @return retorna verdadero o falso, dependiendo del estado del elemento.
     */
    @Override
    public boolean isEnabled(WebElement webElement) {
        return webElement.isEnabled();
    }

    /**
     * Metodo que devuelve si el elemento esta habilitado o no.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     * @return retorna verdadero o falso, dependiendo del estado del elemento.
     */
    @Override
    public boolean isEnabled(WebElement webElement, int timeOutOnSeconds) {
        return waitUntilElementIsVisible(webElement, timeOutOnSeconds).isEnabled();
    }

    /**
     * Metodo que devuelve si el elemento esta visualmente en la ventana o no.
     *
     * @param webElement elemento donde se realizara el comando.
     * @return retorna verdadero o falso, dependiendo del estado del elemento.
     */
    @Override
    public boolean isDisplayed(WebElement webElement) {
        return webElement.isDisplayed();
    }

    /**
     * Metodo que devuelve si el elemento esta visualmente en la ventana o no.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     * @return retorna verdadero o falso, dependiendo del estado del elemento.
     */
    @Override
    public boolean isDisplayed(WebElement webElement, int timeOutOnSeconds) {
        return waitUntilElementIsVisible(webElement, timeOutOnSeconds).isDisplayed();
    }

    /**
     * Metodo que devuelve si el elemento esta seleccionado o no. Utilizado mayormente en checks, radiobuttons.
     *
     * @param webElement elemento donde se realizara el comando.
     * @return retorna verdadero o falso, dependiendo del estado del elemento.
     */
    @Override
    public boolean isSelected(WebElement webElement) {
        return webElement.isSelected();
    }

    /**
     * Metodo que devuelve si el elemento esta seleccionado o no. Utilizado mayormente en checks, radiobuttons.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     * @return retorna verdadero o falso, dependiendo del estado del elemento.
     */
    @Override
    public boolean isSelected(WebElement webElement, int timeOutOnSeconds) {
        return waitUntilElementIsVisible(webElement, timeOutOnSeconds).isSelected();
    }

    /**
     * Metodo que espera que el elemento este visible por un tiempo de espera en segundos.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     * @return retorna el elemento encontrado
     */
    @Override
    public WebElement waitUntilElementIsVisible(WebElement webElement, int timeOutOnSeconds) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeOutOnSeconds))
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Metodo que espera que el elemento sea clickable por un tiempo de espera en segundos.
     *
     * @param webElement       elemento donde se realizara el comando.
     * @param timeOutOnSeconds Tiempo de espera de localizacion.
     * @return retorna el elemento encontrado
     */
    @Override
    public WebElement waitUntilElementIsClickable(WebElement webElement, int timeOutOnSeconds) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeOutOnSeconds))
                .until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Metodo generico que permite esperar un elemento a partir de una condicion esperada y tiempo de duracion para esa condicion
     *
     * @param expectedCondition condicion para el elemento de la clase selenium ExpectedConditions
     *                          Por ejemplo: ExpectedConditions.visibilityOf(element);
     * @param duration          Tiempo de duracion para la condicion esperada de la clase java Duration
     *                          Por Ejemplo: Duration.ofSeconds(2)
     * @return retorna el elemento encontrado.
     */
    @Override
    public WebElement waitUntilElement(ExpectedCondition<WebElement> expectedCondition, Duration duration) {
        return new WebDriverWait(this.driver, duration).until(expectedCondition);
    }

    /**
     * Metodo generico que permite esperar una lista de elementos a partir de una condicion esperada y tiempo de duracion para esa condicion
     *
     * @param expectedCondition condicion para el elemento de la clase selenium ExpectedConditions
     *                          Por ejemplo: ExpectedConditions.visibilityOf(element);
     * @param duration          Tiempo de duracion para la condicion esperada de la clase java Duration
     *                          Por Ejemplo: Duration.ofSeconds(2)
     * @return retorna la lista de elementos encontrado.
     */
    @Override
    public List<WebElement> waitUntilElements(ExpectedCondition<List<WebElement>> expectedCondition, Duration duration) {
        return new WebDriverWait(this.driver, duration).until(expectedCondition);
    }

    /**
     * Espera hasta que la Alerta este presente con un tiempo de duracion
     *
     * @param duration Tiempo de duracion para la condicion esperada de la clase java Duration
     *                 Por Ejemplo: Duration.ofSeconds(2)
     * @return retorna la Alerta encontrada.
     */
    @Override
    public Alert waitUntilAlertIsPresent(Duration duration) {
        return new WebDriverWait(this.driver, duration).until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Limpia un elemento de entrada
     * @param webElement Elemento que se desea limpiar, entrada de caracteres
     */
    public void clear(WebElement webElement){
        webElement.clear();
    }

    /**
     * Limpia un elemento de entrada
     * @param elementLocator Elemento que se desea limpiar, entrada de caracteres
     */
    public void clear(By elementLocator){
        driver.findElements(elementLocator).clear();
    }
}