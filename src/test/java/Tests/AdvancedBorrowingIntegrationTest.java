
package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import services.UserService;
import services.BorrowService;


  
//import services.FineService;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;

public class AdvancedBorrowingIntegrationTest {

    private UserService userService;
    private BorrowService borrowService;
  
    private User testUser;
    private User adminUser;

    @Before
    public void setUp() {
        userService = new UserService();
        borrowService = new BorrowService();
        

        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();

        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

        adminUser = new User("admin", "Admin User", "admin@library.com");
        UserData.addUser(adminUser);

       
        Book book1 = new Book("Book 1", "Author 1", "ISBN001");
        Book book2 = new Book("Book 2", "Author 2", "ISBN002");
        Book book3 = new Book("Book 3", "Author 3", "ISBN003");
        Book book4 = new Book("Book 4", "Author 4", "ISBN004");

        book1.setAvailable(true);
        book2.setAvailable(true);
        book3.setAvailable(true);
        book4.setAvailable(true);

        BookData.addBook(book1);
        BookData.addBook(book2);
        BookData.addBook(book3);
        BookData.addBook(book4);
    }

    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
    }

    
    @Test
public void testCompleteBorrowingRestrictionsScenario() {
    String result1 = borrowService.borrowBook("ISBN001", "USER001");
    assertEquals("Success: Book borrowed successfully", result1);

    String result2 = borrowService.borrowBook("ISBN002", "USER001");
    assertEquals("Success: Book borrowed successfully", result2);

    String result3 = borrowService.borrowBook("ISBN003", "USER001");
    assertEquals("Success: Book borrowed successfully", result3);

    String result4 = borrowService.borrowBook("ISBN004", "USER001");
    assertEquals("Error: Cannot borrow - maximum limit reached", result4);

    Loan overdueLoan = LoanData.getLoansByUser("USER001").get(0);
    overdueLoan.setDueDate(LocalDate.now().minusDays(5)); // جعله متأخر

    String result5 = borrowService.borrowBook("ISBN004", "USER001");
    assertEquals("Error: Cannot borrow - user has overdue books", result5);

    LoanData.clearLoans(); 
    testUser.addFine(15.0);

    String result6 = borrowService.borrowBook("ISBN004", "USER001");
    assertEquals("Error: Cannot borrow - user has unpaid fines", result6);

    boolean canUnregister = userService.canUnregisterUser("USER001");
    assertFalse("Should not be able to unregister with restrictions", canUnregister);

    testUser.setFineBalance(0);
    testUser.setCanBorrow(true);

    String result7 = userService.unregisterUser("USER001", "admin");
    assertEquals("Success: User unregistered successfully", result7);
    assertFalse("User should be inactive", testUser.isActive());
}


    @Test
    public void testErrorMessagesFormat() {
       
        Book overdueBook = BookData.getBookByISBN("ISBN001");
        overdueBook.setAvailable(false); 

        Loan overdueLoan = new Loan(overdueBook, testUser,
                                    LocalDate.now().minusDays(35),
                                    LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);

        String borrowResult1 = borrowService.borrowBook("ISBN002", "USER001");
        assertTrue("Error message should start with 'Error:'", borrowResult1.startsWith("Error:"));
        assertTrue("Error message should contain 'overdue'", borrowResult1.contains("overdue"));

        LoanData.clearLoans();
        testUser.addFine(10.0);

        String borrowResult2 = borrowService.borrowBook("ISBN002", "USER001");
        assertTrue("Error message should start with 'Error:'", borrowResult2.startsWith("Error:"));
        assertTrue("Error message should contain 'unpaid'", borrowResult2.contains("unpaid"));

        String unregisterResult = userService.unregisterUser("USER001", "USER002");
        assertTrue("Error message should start with 'Error:'", unregisterResult.startsWith("Error:"));
        assertTrue("Error message should contain 'administrators'", unregisterResult.contains("administrators"));

        testUser.setFineBalance(0);
        testUser.setCanBorrow(true);

        String successResult = userService.unregisterUser("USER001", "admin");
        assertTrue("Success message should start with 'Success:'", successResult.startsWith("Success:"));
    }
}

