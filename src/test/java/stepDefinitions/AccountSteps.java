package stepDefinitions;

import apiEngine.model.apiservices.EndPoints;
import apiEngine.model.requests.AuthorizationRequest;
import cucumber.TestContext;
import io.cucumber.java.en.Given;

public class AccountSteps extends BaseStep{

	public AccountSteps(TestContext testContext) {
		super(testContext);
		// TODO Auto-generated constructor stub
	}
	
	@Given("I am an authorized user")
	public void i_am_an_authorized_user() {
		
		AuthorizationRequest credentials = new AuthorizationRequest("TEST03", "Test@123");
		getEndPoints().authenticateUser(credentials);
	}
	
}
