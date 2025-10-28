/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steps;

/**
 *
 * @author hp
 */


import io.cucumber.java.en.*;
import org.junit.Assert;
import library.data.AdminData;
import library.data.BookData;
import library.entities.Book;
import library.entities.admin;

public class ServiceSteps {

    private admin admin;

    @Given("the admin is logged in with username {string} and password {string}")
    public void adminIsLoggedIn(String username, String password) {
        admin = AdminData.getAdminByUsername(username);
        Assert.assertNotNull("Admin not found", admin);
        if (admin.getPassword().equals(password)) {
            admin.setLoggedIn(true);
        }
    }
     @Given("the following book exists")
    public void setupBook() {
    Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884");
    BookData.addBook(book);
}
}

