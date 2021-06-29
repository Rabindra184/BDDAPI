package stepDefinitions;

import apiEngine.model.apiservices.EndPoints;
import cucumber.ScenarioContext;
import cucumber.TestContext;

public class BaseStep {
    //private static final String BASE_URL = "https://bookstore.toolsqa.com";
    private EndPoints endPoints;
    private ScenarioContext scenarioContext;
    public BaseStep(TestContext testContext) {
     endPoints = testContext.getEndPoints();
     scenarioContext=testContext.getScenarioContext();
     
    }
 
    public EndPoints getEndPoints() {
        return endPoints;
    }
    
    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
