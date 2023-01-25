package com.nttdata.ct.web.base.dom;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface IShadowRoot {

    /**
     * Obtiene el contexto de shadowRoot a partir del elemento web contenedor del shadowRoot abierto.
     * A partir del mismo contexto ya se puede identificar al elemento dentro del shadowRoot
     *
     * @param element Elemento web identificado que contiene un shadowRoot
     * @return El valor del contexto de shadowRtoot
     */
    SearchContext getContext(WebElement element);

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
    WebElement getWebElement(WebElement firstContentRoot, String cssLocatorStrategy);

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
    List<WebElement> getWebElements(WebElement firstContentRoot, String cssLocatorStrategy, String cssLocatorList);
}
