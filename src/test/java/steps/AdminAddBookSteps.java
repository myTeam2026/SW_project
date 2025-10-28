/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    package steps;

import library.entities.Book;
import library.data.BookData;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class AdminAddBookSteps {

    private Book addedBook;


    @When("the admin adds a book with title {string}, author {string}, and ISBN {string}")
    public void adminAddsBook(String title, String author, String isbn) {
        addedBook = new Book(title, author, isbn);
        BookData.addBook(addedBook);
    }

    @Then("the book should be added successfully")
    public void bookShouldBeAdded() {
        Assert.assertTrue("Book should exist in the system", BookData.getBookByISBN(addedBook.getIsbn()) != null);
    }

    @Then("the book should be searchable by title")
    public void bookShouldBeSearchable() {
        Assert.assertTrue("Book should be searchable by title", BookData.searchBooksByTitle(addedBook.getTitle()).contains(addedBook));
    }
}


