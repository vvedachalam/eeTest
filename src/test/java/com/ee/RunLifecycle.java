package com.ee;

import com.ee.context.Context;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:cucumber.xml")
public class RunLifecycle {

    @Autowired
    private Context context;

    @Before
    public void beforeScenario(Scenario scenario) {
        context.setUp(scenario);
    }

    @Given("^pending$")
    public void pending() {
        throw new PendingException();
    }

    @After
    public void afterScenario(Scenario scenario) {
        context.afterScenarioFailedReport(scenario);
        context.tearDown();
    }


}
