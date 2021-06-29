package stepDefinitions;
import org.junit.Assert;
import apiEngine.model.apiservices.IRestResponse;
import apiEngine.model.response.Books;
import apiEngine.model.response.UserAccount;
import apinEngine.model.Book;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class VerificationSteps extends BaseStep {

    public VerificationSteps(TestContext testContext) {
        super(testContext);
    }
	
	
	  @Then("the book is added") public void bookIsAdded() { Book book =
	  (Book)getScenarioContext().getContext(Context.BOOK); String userId = (String)
	  getScenarioContext().getContext(Context.USER_ID); 
	  IRestResponse<Books> BooksRespon
	   = (IRestResponse<Books>)
	  getScenarioContext().getContext(Context.BOOKS);
	  Assert.assertEquals(201, BooksRespon.getStatusCode());
	  Assert.assertTrue(BooksRespon.isSuccessful());
	  Assert.assertEquals(book.isbn, BooksRespon.getBody().books.get(0).isbn);
	   }
	  
	  @Then("the book is removed") public void bookIsRemoved() { String userId =
	  (String) getScenarioContext().getContext(Context.USER_ID); Response response
	  = (Response) getScenarioContext().getContext(Context.BOOK_REMOVE_RESPONSE);
	  Assert.assertEquals(204, response.getStatusCode());
	  IRestResponse<UserAccount> userAccountResponse =
	  getEndPoints().getUserAccount(userId); Assert.assertEquals(200,
	  userAccountResponse.getStatusCode()); Assert.assertEquals(0,
	  userAccountResponse.getBody().books.size()); }
	 
}