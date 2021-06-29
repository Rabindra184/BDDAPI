package cucumber;

import apiEngine.model.apiservices.EndPoints;
import dataProvider.ConfigReader;
import enums.Context;

public class TestContext {
	private EndPoints endPoints;
	private ScenarioContext scenarioContext;
	
	public TestContext() {
		endPoints = new EndPoints(ConfigReader.getInstance().getBaseUrl());
		scenarioContext=new ScenarioContext();
		scenarioContext.setContext(Context.USER_ID,ConfigReader.getInstance().getUserID());
	}
	
	 public EndPoints getEndPoints() {
        return endPoints;
    }
	 
	 public ScenarioContext getScenarioContext() {
			return scenarioContext;
		}
}
