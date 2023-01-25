package com.nttdata.ct.web.jira;

import com.nttdata.ct.web.service.constans.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jira-integration.properties")
public class JiraProperties {

    @Value("${jxray.host:NOT_DEFINED}")
    protected String jiraHost;
    @Value("${jxray.client.id:NOT_DEFINED}")
    protected String clientId;
    @Value("${jxray.client.secret:NOT_DEFINED}")
    protected String clientSecret;
    @Value("${jxray.integration:false}")
    protected boolean jxrayIntegration;

    @Value("${jira.cucumber.import.execution}")
    protected String jiraCucumberImportEndPoint;

    @Value("${jira.authenticate}")
    protected String jiraAuthEndPoint;

    protected boolean jxrayPropertiesDefined() { //TODO: mejorar logica
        return (isNotDefinided(jiraHost) || isNotDefinided(clientId) || isNotDefinided(clientSecret));
    }

    private static boolean isNotDefinided(String property) {
        return property.equals(Constants.NOT_DEFINED);
    }

}
