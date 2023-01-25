package com.nttdata.ct.web.page;

import com.nttdata.ct.web.base.WebBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleSearchPage extends WebBase {

    @FindBy(xpath = "//input[@class='gLFyf gsfi']")
    protected WebElement searchInput;
    @FindBy(xpath = "(//input[@class='gNO89b'])[1]")
    protected WebElement buscarButton;

    public void writeSearch(String data) {
        type(searchInput, data);
    }

    public void search() {
        click(buscarButton, 10);
    }

}