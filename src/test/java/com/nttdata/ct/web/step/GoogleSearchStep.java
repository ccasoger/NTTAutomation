package com.nttdata.ct.web.step;

import com.nttdata.ct.web.service.aspect.evidence.ScreenShot;
import com.nttdata.ct.web.service.aspect.log.LogTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ScreenShot
@LogTime
public class GoogleSearchStep {

    @Autowired
    private StepPages page;

    public void searchData(String data) {
        page.searchPage().writeSearch(data);
        page.searchPage().search();
    }

    public String getSearchResults() {
        return page.resultsPage().getSearchResults();
    }

}