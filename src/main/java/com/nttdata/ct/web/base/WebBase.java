package com.nttdata.ct.web.base;

import com.nttdata.ct.web.base.dom.IFindBy;
import com.nttdata.ct.web.base.dom.IShadowRoot;
import com.nttdata.ct.web.base.dom.IWebBase;
import com.nttdata.ct.web.base.dom.IWebDriverJavaScript;
import com.nttdata.ct.web.base.methods.FindByDom;
import com.nttdata.ct.web.base.methods.ShadowRoot;
import com.nttdata.ct.web.base.methods.WebDriverDom;
import com.nttdata.ct.web.base.methods.WebDriverJavaScriptDom;
import com.nttdata.ct.web.lib.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase base que permite el acceso a metodos que abstraen la clase WebDriver.
 * <p>
 * Interfaces expuestas:
 * js(): nos da acceso a metodos que utilizan la clase JavascriptExecutor de Selenium.
 * find(): nos da acceso a metodos que tienen distintas estrategias de localizacion de elementos,
 * quitando la necesidad de utilizar la lina de localizacion: driver.findElement | driver.findElements
 * actions(): nos da acceso a metodso que utilizan la clase Actions de Selenium.
 */
public class WebBase extends WebDriverDom implements IWebBase {

    public WebBase() {
        PageFactory.initElements(WebDriverManager.getDriver(), this);
    }

    protected WebDriver driver() {
        return WebDriverManager.getDriver();
    }

    /**
     * Crea la instancia de la clase WebDriverWait
     *
     * @param duration tiempo de duracion de espera antes del timeOut a traves de la clase Duration
     * @return retorna la instancia creada de la clase WebDriverWait
     */
    protected WebDriverWait webDriverWait(Duration duration) {
        return new WebDriverWait(driver(), duration);
    }

    /**
     * Interface que Accede a metodos que ejecutan comandos con JavascriptExecutor
     *
     * @return retorna el acceso a los metodos
     */
    @Override
    public IWebDriverJavaScript js() {
        return new WebDriverJavaScriptDom();
    }

    /**
     * Interface que Accede a metodos que ubican elementos por estrategia de localizacion
     *
     * @return retorna el acceso a los metodos
     */
    @Override
    public IFindBy find() {
        return new FindByDom();
    }

    /**
     * Interface que Accede a metodos que realizan comandos de la clase Actions
     *
     * @return retorna el acceso a los metodos
     */
    @Override
    public Actions actions() {
        return new Actions(driver());
    }

    /**
     * Interface que Accede a metodos que ubican elementos por estrategia de localizacion
     *
     * @return retorna el acceso a los metodos
     */
    @Override
    public IShadowRoot sh() {
        return new ShadowRoot();
    }


}