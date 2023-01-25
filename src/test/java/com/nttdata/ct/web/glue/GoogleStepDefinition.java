package com.nttdata.ct.web.glue;

import com.nttdata.ct.web.lib.WebDriverManager;
import com.nttdata.ct.web.service.ddt.DataDriven;
import com.nttdata.ct.web.step.GoogleSearchStep;
import com.nttdata.ct.web.step.GoogleSettingsStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
public class GoogleStepDefinition {

    @Autowired
    private DataDriven data;

    @Autowired
    private GoogleSearchStep searchStep;
    @Autowired
    private GoogleSettingsStep settingsStep; //demo shadowRoot

    @Value("${url.google}")
    private String urlGoogle;
    @Value("${url.settings}")
    private String urlGoogleSettings;

    @Autowired
    private WebDriverManager manager;

    @Given("que abro la pagina de google")
    public void queAbroLaPaginaDeGoogle() {
        manager.navigateTo(urlGoogle);
    }

    @Given("que abro la pagina de google - parallel")
    public void queAbroLaPaginaDeGoogleParallel() {
        manager.navigateTo(urlGoogle);
    }

    @When("escribo la busqueda de: {string}")
    public void escriboLaBusquedaDe(String busqueda) {
        searchStep.searchData(busqueda);
    }

    @Then("valido que los resultados sean mayores a {int}")
    public void validoQueLosResultadosSeanMayoresA(int range) {
        Assert.assertTrue(searchStep.getSearchResults().length() > range);
    }

    @Given("que abro la pagina de configuracio de google")
    public void queAbroLaPaginaDeConfiguracioDeGoogle() {
        manager.navigateTo(urlGoogleSettings);
    }

    @When("busco la opcion {string}")
    public void buscoLaOpcion(String option) {
        settingsStep.searchSettings(option);
    }

    @Then("valido que el resultado sea {string}")
    public void validoQueElResultadoSea(String result) {
        Assert.assertEquals("El valor del resultado obtenido no corresponde con el valore esperado.",
                result, settingsStep.getValueResult());
    }

}