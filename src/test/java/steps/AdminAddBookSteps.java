
    package steps;

import library.entities.Book;
import library.data.BookData;
import io.cucumber.java.en.*;
import java.util.List;
import library.entities.Admin;
import org.junit.Assert;

/*public class AdminAddBookSteps {

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
}*/
public class AdminAddBookSteps {

    private final ServiceSteps serviceSteps;

    public AdminAddBookSteps(ServiceSteps serviceSteps) {
        this.serviceSteps = serviceSteps;
    }
    
  public AdminAddBookSteps() {
        // Cucumber يحتاج هذا الـ constructor الفارغ
        this.serviceSteps = new ServiceSteps(); // إنشاء كائن جديد افتراضيًا
    }

    

    @When("the admin adds a book with title {string}, author {string}, and ISBN {string}")
    public void adminAddsBook(String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        BookData.addBook(book);
        serviceSteps.setSearchResults(List.of(book)); // لتسهيل البحث بعد الإضافة
    }

    @Then("the book should be added successfully")
    public void bookShouldBeAdded() {
        Book book = serviceSteps.getSearchResults().get(0);
        Assert.assertTrue("Book should exist in the system",
                BookData.getBookByISBN(book.getIsbn()) != null);
    }

    @Then("the book should be searchable by title")
    public void bookShouldBeSearchable() {
        Book book = serviceSteps.getSearchResults().get(0);
        Assert.assertTrue("Book should be searchable by title",
                BookData.searchBooksByTitle(book.getTitle()).contains(book));
    }
}



