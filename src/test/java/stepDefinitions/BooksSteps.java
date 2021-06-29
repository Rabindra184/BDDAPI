package stepDefinitions;

import org.junit.Assert;

import apiEngine.model.apiservices.IRestResponse;
import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.response.Books;
import apiEngine.model.response.UserAccount;
import apinEngine.model.Book;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class BooksSteps extends BaseStep{
	
	private static Book book;
	private static UserAccount userAccount;
	IRestResponse<UserAccount> userAccountResponse;
	IRestResponse<Books> BooksResponse;
	

	public BooksSteps(TestContext testContext) {
		super(testContext);
		// TODO Auto-generated constructor stub
	}
	
	

	@Given("A list of books are available")
	public void a_list_of_books_are_available() {
		IRestResponse<Books> booksRespon = getEndPoints().getBooks();
		Assert.assertTrue(booksRespon.getBody().books.size() > 0);
		// This bookId will be used in later requests, to add the book with respective
		// isbn
		book = booksRespon.getBody().books.get(0);
		getScenarioContext().setContext(Context.BOOK, book);
	}

	@When("I add a book to my reading list")
	public void i_add_a_book_to_my_reading_list() {
		Book book=(Book) getScenarioContext().getContext(Context.BOOK);
		String userid=(String) getScenarioContext().getContext(Context.USER_ID);
		
		AddBooksRequest addBooksRequest = new AddBooksRequest(userid, new ISBN(book.isbn));
		BooksResponse = getEndPoints().addBook(addBooksRequest);
		getScenarioContext().setContext(Context.BOOKS,BooksResponse);
	}
	
	/*
	 * @Then("the book is added") public void the_book_is_added() { //
	 * Assert.assertEquals(201, userAccountResponse.getStatusCode()); }
	 */
	 

	@When("I remove a book from my reading list")
	public void i_remove_a_book_from_my_reading_list() {
		Book book=(Book) getScenarioContext().getContext(Context.BOOK);
		String userid=(String) getScenarioContext().getContext(Context.USER_ID);
		RemoveBookRequest removeBookRequest = new RemoveBookRequest(userid, book.isbn);
		Response response=getEndPoints().removeBook(removeBookRequest);
		getScenarioContext().setContext(Context.BOOK_REMOVE_RESPONSE, response);
	}

	/*
	 * @Then("the book is removed") public void the_book_is_removed() { String
	 * userid=(String) getScenarioContext().getContext(Context.USER_ID);
	 * userAccountResponse = getEndPoints().getUserAccount(userid); userAccount =
	 * userAccountResponse.getBody(); Assert.assertEquals(0,
	 * userAccount.books.size()); }
	 */
}
