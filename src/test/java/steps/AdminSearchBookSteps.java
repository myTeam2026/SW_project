/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steps;

/**
 *
 * @author hp
 */


import library.entities.Book;
import library.data.BookData;
import io.cucumber.java.en.*;
import org.junit.Assert;
import java.util.List;

public class AdminSearchBookSteps {

    private List<Book> searchResults;
   


    @When("the user searches for a book with title {string}")
    public void searchBookByTitle(String title) {
        searchResults = BookData.searchBooksByTitle(title);
    }

    @When("the user searches for a book with author {string}")
    public void searchBookByAuthor(String author) {
        searchResults = BookData.searchBooksByAuthor(author);
    }

    @When("the user searches for a book with ISBN {string}")
    public void searchBookByISBN(String isbn) {
        Book book = BookData.getBookByISBN(isbn);
        searchResults = (book != null) ? List.of(book) : List.of();
    }

    @Then("the search results should include a book with title {string}")
    public void verifyBookByTitle(String title) {
        Assert.assertTrue("Book with title should exist", 
            searchResults.stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(title)));
    }

    @Then("the search results should include a book with author {string}")
    public void verifyBookByAuthor(String author) {
        Assert.assertTrue("Book with author should exist", 
            searchResults.stream().anyMatch(b -> b.getAuthor().equalsIgnoreCase(author)));
    }

    @Then("the search results should include a book with ISBN {string}")
    public void verifyBookByISBN(String isbn) {
        Assert.assertTrue("Book with ISBN should exist", 
            searchResults.stream().anyMatch(b -> b.getIsbn().equals(isbn)));
    }
}
 

