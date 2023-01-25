package com.nttdata.ct.web.base.methods;

import com.nttdata.ct.web.base.dom.IShadowRoot;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShadowRoot implements IShadowRoot {

    /**
     * Obtiene el contexto de shadowRoot a partir del elemento web contenedor del shadowRoot abierto.
     * A partir del mismo contexto ya se puede identificar al elemento dentro del shadowRoot
     *
     * @param element Elemento web identificado que contiene un shadowRoot
     * @return El valor del contexto de shadowRtoot
     */
    @Override
    public SearchContext getContext(WebElement element) {
        return element.getShadowRoot();
    }

    /**
     * Obtiene un elemento web a traves de una estrategia de localización de elementos con cssLocator.
     * <p>
     * Es necesario mandar el elemento web contenedor del primer shadowRoot.
     * El segundo atributo 'cssLocatorStrategy' es un cadena de Strings que contiene los localizadores de tipo cssLocator
     * de cada contenedor de shadowRoot separa por el simbolo ';', hasta llegar al elemento requerido.
     *
     * @param firstContentRoot   Primero elemento web identificado que contiene un shadowRoot.
     * @param cssLocatorStrategy Cadena de Strings que contiene los localizadores de tipo cssLocator
     *                           de cada contenedor de shadowRoot separa por el simbolo ';', hasta llegar al elemento requerido.
     *                           Por ejemplo: cssSelector1;cssSelector2;cssSelector3;cssSelectorDelElementRequerido
     * @return El elemento encontrado en el o los shadowRoot
     */
    @Override
    public WebElement getWebElement(WebElement firstContentRoot, String cssLocatorStrategy) {
        var path = cssLocatorStrategy.split(";");
        Binding binding = new Binding();
        binding.setVariable("root", firstContentRoot);
        By byCssSelector;
        StringBuilder rootBuilder = new StringBuilder("root");
        for (int i = 0; i < path.length; i++) {
            byCssSelector = By.cssSelector(path[i]);
            binding.setVariable("byCssSelector" + i, byCssSelector);
            rootBuilder.append(".getShadowRoot().findElement(" + "byCssSelector").append(i).append(")");
        }
        GroovyShell shell = new GroovyShell(binding);
        return (WebElement) shell.evaluate(rootBuilder.toString());
    }

    /**
     * Obtiene una lista de elementos web a traves de una estrategia de localización de elementos con cssLocator.
     * <p>
     * Es necesario mandar el elemento web contenedor del primer shadowRoot.
     * El segundo atributo 'cssLocatorStrategy' es un cadena de Strings que contiene los localizadores de tipo cssLocator
     * de cada contenedor de shadowRoot separa por el simbolo ';', hasta llegar al elemento requerido.
     *
     * @param firstContentRoot   Primero elemento web identificado que contiene un shadowRoot.
     * @param cssLocatorStrategy Cadena de Strings que contiene los localizadores de tipo cssLocator
     *                           de cada contenedor de shadowRoot separa por el simbolo ';', hasta llegar al elemento requerido.
     *                           Por ejemplo: cssSelector1;cssSelector2;cssSelector3;cssSelectorDelElementRequerido
     * @param cssLocatorList Localizador de tipo cssSelector de los elementos web requeridos
     * @return El elemento encontrado en el o los shadowRoot
     */
    @Override
    public List<WebElement> getWebElements(WebElement firstContentRoot, String cssLocatorStrategy, String cssLocatorList){
        WebElement element = getWebElement(firstContentRoot, cssLocatorStrategy);
        return element.getShadowRoot().findElements(By.cssSelector(cssLocatorList));
    }

}
