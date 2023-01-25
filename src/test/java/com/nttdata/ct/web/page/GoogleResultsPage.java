package com.nttdata.ct.web.page;

import com.nttdata.ct.web.base.WebBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleResultsPage extends WebBase {

    @FindBy(xpath = "//div[@id='result-stats']")
    protected WebElement resultsLabel;

    public String getSearchResults() {
        return getText(resultsLabel);
    }
}
