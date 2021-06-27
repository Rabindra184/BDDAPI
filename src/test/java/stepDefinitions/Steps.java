package stepDefinitions;

import org.junit.Assert;

import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.requests.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.response.Books;
import apiEngine.model.response.Token;
import apiEngine.model.response.UserAccount;
import apinEngine.model.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Steps {

	private static final String USER_ID = "14b3b9f3-ebb6-49a0-a694-78297aa2a69a";
	private static final String USERNAME = "TEST03";
	private static final String PASSWORD = "Test@123";
	private static final String BASE_URL = "https://demoqa.com/";

	private static Response response;
	private static Token tokenResponse;
	private static Book book;
	private static Books books;
	private static UserAccount userAccount;

	@Given("I am an authorized user")
	public void i_am_an_authorized_user() {

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-type", "application/json");
		AuthorizationRequest credentials = new AuthorizationRequest(USERNAME, PASSWORD);
		response = request.body(credentials).post("/Account/v1/GenerateToken");

		tokenResponse = response.getBody().as(Token.class);
	}

	@Given("A list of books are available")
	public void a_list_of_books_are_available() {

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get("/BookStore/v1/Books");
		books = response.getBody().as(Books.class);

		Assert.assertTrue(books.books.size() > 0);

		// This bookId will be used in later requests, to add the book with respective
		// isbn
		book = books.books.get(0);

	}

	@When("I add a book to my reading list")
	public void i_add_a_book_to_my_reading_list() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + tokenResponse.token).header("Content-Type", "application/json");

		AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, new ISBN(book.isbn));

		response = request.body(addBooksRequest).post("/BookStore/v1/Books");
	}

	@Then("the book is added")
	public void the_book_is_added() {
		Assert.assertEquals(201, response.getStatusCode());
			
	}

	@When("I remove a book from my reading list")
	public void i_remove_a_book_from_my_reading_list() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();

		request.header("Authorization", "Bearer " + tokenResponse.token).header("Content-Type", "application/json");

		RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, book.isbn);

		response = request.body(removeBookRequest).delete("/BookStore/v1/Book");

	}

	@Then("the book is removed")
	public void the_book_is_removed() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + tokenResponse.token).header("Content-Type", "application/json");
		response = request.get("Account/v1/User/" + USER_ID);

		userAccount = response.getBody().as(UserAccount.class);

		Assert.assertEquals(0, userAccount.books.size());

	}

}
