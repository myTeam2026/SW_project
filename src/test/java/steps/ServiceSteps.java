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
import java.util.List;
import org.junit.Assert;
import library.data.AdminData;
import library.data.BookData;
import library.entities.Book;
import library.entities.Admin;

/*
public class ServiceSteps {

    private admin admin;

    public void loginAdmin(String username, String password) {
        admin = AdminData.getAdminByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            admin.setLoggedIn(true);
        }}



    @Given("the admin is logged in with username {string} and password {string}")
    public void adminIsLoggedIn(String username, String password) {
        admin = AdminData.getAdminByUsername(username);
        Assert.assertNotNull("Admin not found", admin);
        Assert.assertEquals("Wrong password", password, admin.getPassword());
         if (admin.getPassword().equals(password)) {
            admin.setLoggedIn(true);
        }
        admin.setLoggedIn(true);
    }

    public admin getLoggedAdmin() {
        return admin;
    }

    public void setLoggedAdmin(admin admin) {
        this.admin = admin;
    }



     @Given("the following book exists")
    public void setupBook() {
    Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884");
    BookData.addBook(book)
}

}
*/public class ServiceSteps {

    private Admin loggedAdmin; // admin الحالي المتصل
    private Book currentBook;   // الكتاب الحالي للاختبارات
    private List<Book> searchResults; // نتائج البحث

    // تسجيل الدخول وإعادة استخدامه في كل الخطوات
    public Admin getAdminByUsername(String username) {
        return AdminData.getAdminByUsername(username);
    }

    public void setLoggedAdmin(Admin admin) {
        this.loggedAdmin = admin;
    }

    public Admin getLoggedAdmin() {
        return loggedAdmin;
    }

    // إنشاء كتاب واحد للاختبارات
    
@Given("the admin is logged in with username {string} and password {string}")
public void adminIsLoggedIn(String username, String password) {
    Admin admin = AdminData.getAdminByUsername(username); // حرف صغير للمتغير
    Assert.assertNotNull("Admin not found", admin);

    if (admin.getPassword().equals(password)) {
        admin.setLoggedIn(true);
    }

    this.loggedAdmin = admin; // تخزينه في المتغير المشترك للاستخدام لاحقًا
}

    @Given("the following book exists")
    public void setupBook() {
        currentBook = new Book("Clean Code", "Robert C. Martin", "9780132350884");
        BookData.addBook(currentBook);
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setSearchResults(List<Book> results) {
        this.searchResults = results;
    }

    public List<Book> getSearchResults() {
        return searchResults;
    }
}