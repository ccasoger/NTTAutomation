package com.nttdata.ct.web.step;

import com.nttdata.ct.web.page.GoogleResultsPage;
import com.nttdata.ct.web.page.GoogleSearchPage;
import com.nttdata.ct.web.page.GoogleSettingsPage;
import org.springframework.stereotype.Component;

@Component
public class StepPages {

    protected GoogleSearchPage searchPage() {
        return new GoogleSearchPage();
    }

    protected GoogleResultsPage resultsPage() {
        return new GoogleResultsPage();
    }

    protected GoogleSettingsPage settinsPage() {
        return new GoogleSettingsPage();
    }

}
