package com.nttdata.ct.web.service.ddt;

import com.nttdata.ct.web.service.ddt.engine.ExcelReader;
import com.nttdata.ct.web.service.ddt.engine.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataDriven {

    private final ExcelReader excel;
    private final JsonReader json;

    @Autowired
    DataDriven(ExcelReader excel, JsonReader json) {
        this.excel = excel;
        this.json = json;
    }

    public ExcelReader excel() {
        return this.excel;
    }

    public JsonReader json() {
        return this.json;
    }

}
