package com.nttdata.ct.web.page;

import com.nttdata.ct.web.base.WebBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleSettingsPage extends WebBase {

    @FindBy(tagName = "settings-ui")
    private WebElement root;

    private String searchInput = "cr-toolbar#toolbar;cr-toolbar-search-field#search;input#searchInput";
    private String result = "settings-main;settings-basic-page;#basicPage settings-section[page-title=Autocompletar];#header h2";

    public void searchSettings(String option) {
        WebElement inputElement = sh().getWebElement(root, searchInput);
        inputElement.sendKeys(option);
    }

    public String getValueResult() {
        WebElement resultElement = sh().getWebElement(root, result);
        return getText(resultElement);
    }
}