# Propiedades

En este documento se encuentran todas las propiades soportadas por el Framework que permiten la configuración de ejecución de la prueba automatizada.

### PROPIEDADES COMUNES

**-webdriver.implicitWaitOnSeconds**= Define el tiempo implicito (antes del timeout) de carga y de localización de elementos.

**-webdriver.cicd**= Activa y desactiva la configuración de la propiedad **'System.setProperty(FIREFOX_PROPERTY, driverPath);'** preparando la ejecución y busqueda directa de los drivers configurados en las variables de entorno del contexto de ejecución.

**-webdriver.path**= Ruta de ubicación del driver de ejecución, este driver debe ser compatible con el nombre indicado en la
propiedad **_'webdriver.browser'_**

###### **Inscribir rutas de drivers locales en un mismo bloque**
Si se agregan estas propiedades, la propiedad **-webdriver.path** debe ir vacia o no definida.

```
-webdriver.path.chrome= Ubicación del driver 'chromedriver'

-webdriver.path.firefox= Ubicación del driver 'geckodriver'

-webdriver.path.edge= Ubicación del driver 'msedgedriver'

* En el caso de Safari, Selenium localiza directamente la ubicación del binario.
```

**-webdriver.browser**= Nombre del browser donde se va a ejecutar la prueba. Valores soportados:

```
chrome | firefox |  safari | edge

Ejemplo:
-webdriver.browser=chrome
```

**-webdriver.pageload**= Define la estrageia de carga de las páginas durante la ejecución. Valores aceptados: NORMAL, EAGER, NONE
```
* NORMAL: Valor por defecto, Selenium esperara a que toda la pagina haya terminado de cargar.
* EAGER: Esta propiedad hará que Selenium espere solo a que cargue el HTML Document y obviara los estilos, imagenes y subframes.
* NONE: Selenium solo esperara a que la pagina inicial se haya descargado. 
```

**-webdriver.size**= Propiedad que permite iniciar con un tamaño especifico de la ventana: MAXIMIZE, FULLSCREEN, NONE

```
* MAXIMIZE: [Valor por defecto]. Si no se declara la propiedad "webdriver.size" el valor por defeco será "MAXIMIZE" para maximizar la ventana del browser.
* FULLSCREEN: Amplia la ventana del browser a Pantalla completa
* NONE | [o cualquier otro valor]: No es soportado por ningun tipo de tamaño de la ventana. Se abrirá la ventana con un tamaño indefinido.

Esta propiedad opción tambien puede ser activada por código:

@Autowired
private WebDriverManager manager;
...
manager.fullScreen();
manager.maximize();
```

**-webdriver.headless**= Valor de tipo boolean (true|false) que, indica si la ejecución será de tipo background.

**-webdriver.headless.dimension**= Argumento solo compatible con los drivers 'firefox|chrome' que, indica la dimensión en la
que se dimensionará el browser levantado en headLess. Esta propiedad solo se podrá usar si la propiedad '
web.driver.headless' tiene como valor 'true'

````
Ejemplo:

- application.properties

    webdriver.headless=true
    webdriver.headless.dimension=--window-size=1400,600
    
...

- application.yml

    webdriver:
        headless: on
        headless.dimension: --window-size=1400,600

````

**-webdriver.accept.insecure.certs**= Valor de tipo boolean (true|false) que, habilita la navegacion con certificados
inseguros.

### PROPIEDADES PARA CHROME BROWSER

**-webdriver.chrome.device**= Ejecuta la instancia de chrome bajo las dimensiones del nombre de un dispositivo mobile soportado por el browser.

````
Posibles valores:
Moto G4
Galaxy S5
Pixel 2
Pixel 2 XL
iPhone 5/SE
iPhone 6/7/8
iPhone 6/7/8 Plus
iPhone X
iPad
iPad Pro
````

###### **DEFINIR UN TAMAÑO CUSTOMIZADO**

**-webdriver.chrome.device.width**= Dimensión de la pantalla, ancho.

**-webdriver.chrome.device.height**= Dimensión de la pantalla, alto.

**-webdriver.chrome.device.pixelRatio**= Ratio de pixeles. Por defecto '0.3'

**-webdriver.chrome.extraArgs**= Argumentos extras separados por un ','.

````
Ejemplo:
-webdriver.chrome.extraArgs= arg1,arg2,arg3,...n
````


### EJECUCION REMOTA

**-webdriver.remote**= propiedad de tipo 'boolean' que indica si la ejecución es remota. Valor por defecto 'false'

**-webdriver.hub**= indica la URL del HUB (remoto o local) configurado de Selenium, con el siguiente formato:

```
-webdriver.remote=true
-webdriver.hub=http://<IP-SERVER>:<PORT>/wd/hub
```

### PROPIEDADES PARA FIREFOX BROWSER

**-webdriver.firefox.extraArgs**= Argumentos extras separados por un ','.

````
Ejemplo:
-webdriver.firefox.extraArgs= arg1,arg2,arg3,...n
````

### URL SITES (OPTIONAL)

Se pueden declarar las urls de navegación en el archivo de propiedad. Para utilizar esta funcionalidad y poder
obtener la url es necesario llamar a la anotación de srpingboot '@Value' y asignarle una variable.

```
* application.properties:
* application.yml:

-url.google= https://www.google.com/

* Clase 'glue' o 'stepDefinition':

@CucumberContextConfiguration
@SpringBootTest
public class GoogleStepDefinition {

...
    @Value("${url.google}")
    private String urlGoogle;
...

    @Given("que abro la pagina de google")
    public void queAbroLaPaginaDeGoogle() {
      manage.setUpDriver();
      manage.navigateTo(urlGoogle);
    }

}
```

### ARCHIVO DE PROPIEDADES

SpringBoot gestiona las propiedades de configuración a través del archivo **_'application.properties'_** o **_'application.yml'_**.

### PERFILES Y AMBIENTES

Podemos crear perfiles (ambientes) de ejecución agregando un sufijo al nombre del archivo de propiedades principal y posteriormente
cambiar el ambiente a través de comandos con el nombre del sufijo creado.

```
test
 - resources
    - application.yml
    - application-qa.yml
    - application-dev.yml
    
Comando: 
   $ mvn ... -Dspring.profiles.active=qa
   $ mvn ... -Dspring.profiles.active=dev

```

## YAML BASICO DE CONFIGURACION INICIAL

### **resources/application.yml**

```
webdriver:
  path: <ubicacion_del_driver>
  browser: chrome
  size: MAXIMIZE
```

## PROPERTY BASICO DE CONFIGURACION INICIAL

### **resources/application.propertie**

```
webdriver.path= <ubicacion_del_driver>
webdriver.browser= chrome
webdriver.size= MAXIMIZE
```

## CONFIGURACION INICIAL (EJEMPLO)

### **resources/application.yml**

Copiar y pegar la siguiente configuracion en el archivo **application.yml**. Cambiar el valor de la propiedad **path** con la ruta real del driver de ejecución.

```
webdriver:
  browser: chrome
  path: drivers/mac/chromedriver
```

```
webdriver:
  browser: chrome
  path: 
    chrome: drivers/mac/chromedriver
    firefox: drivers/mac/geckodriver
    edge: drivers/mac/msedgedriver
```