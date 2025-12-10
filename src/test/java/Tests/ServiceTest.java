package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;

import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.services.BorrowService;
import library.services.FineService;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;

/**
 * Test class for library services including BorrowService and FineService.
 * <p>
 * This class contains JUnit tests for scenarios such as borrowing books, 
 * handling fines, returning books, and restrictions on overdue books.
 * </p>
 * 
 * @author همسة حنتش
 * @version 1.0
 */
public class ServiceTest {
    
    /** Borrow service instance */
    private BorrowService borrowService;
    
    /** Fine service instance */
    private FineService fineService;
    
    /** Test user */
    private User testUser;
    
    /** Test book */
    private Book testBook;
    
    /**
     * Set up the test environment before each test.
     * <p>
     * Initializes services, clears previous data, and adds a test user and a test book.
     * </p>
     */
    @Before
    public void setUp() {
        borrowService = new BorrowService();
        fineService = new FineService();
        
        BookData.clearBooks();
        UserData.clearUsers();
        LoanData.clearLoans();
        
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
        
        testBook = new Book("Test Book", "Test Author", "ISBN001");
        testBook.setAvailable(true);
        BookData.addBook(testBook);
    }
    
    /**
     * Tear down the test environment after each test.
     * <p>
     * Clears all books, users, and loans.
     * </p>
     */
    @After
    public void tearDown() {
        BookData.clearBooks();
        UserData.clearUsers();
        LoanData.clearLoans();
    }
    
    /**
     * Test an integrated scenario of borrowing a book, adding a fine, 
     * and paying the fine.
     * 
     * @author همسة حنتش
     * @return void
     */
    @Test
    public void testIntegratedBorrowAndFineScenario() {
        String borrowResult = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Success: Book borrowed successfully", borrowResult);
        assertFalse("Book should not be available", testBook.isAvailable());
        
        fineService.addFine("USER001", 25.0);
        assertFalse("User should not be able to borrow with fine", testUser.canBorrow());
        
        boolean paymentResult = fineService.payFine("USER001", 25.0);
        assertTrue("Payment should be successful", paymentResult);
        assertTrue("User should be able to borrow after fine payment", testUser.canBorrow());
    }
    
    /**
     * Test that a user with an active fine cannot borrow a book.
     * 
     * @author همسة حنتش
     * @return void
     */
    @Test
    public void testCannotBorrowWithActiveFine() {
        fineService.addFine("USER001", 10.0);
        String borrowResult = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Error: Cannot borrow - user has unpaid fines", borrowResult);
        assertTrue("Book should still be available", testBook.isAvailable());
    }
    
    /**
     * Test borrowing a book after paying an existing fine.
     * 
     * @author همسة حنتش
     * @return void
     */
    @Test
    public void testBorrowAfterFinePayment() {
        fineService.addFine("USER001", 15.0);
        fineService.payFine("USER001", 15.0);
        
        String borrowResult = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Success: Book borrowed successfully", borrowResult);
        assertTrue("User should have borrowing rights", testUser.canBorrow());
    }
    
    /**
     * Test the scenario of returning a borrowed book.
     * 
     * @author همسة حنتش
     * @return void
     */
    @Test
    public void testReturnBookScenario() {
        borrowService.borrowBook("ISBN001", "USER001");
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        
        boolean returnResult = borrowService.returnBook(loan.getLoanId());
        assertTrue("Return should be successful", returnResult);
        assertTrue("Book should be available again", testBook.isAvailable());
        
        String borrowAgain = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Success: Book borrowed successfully", borrowAgain);
    }
    
    /**
     * Test that a user with overdue books cannot borrow new books.
     * 
     * @author همسة حنتش
     * @return void
     */
    @Test
    public void testBorrowWithOverdueBooks() {
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        Book newBook = new Book("New Book", "New Author", "ISBN002");
        newBook.setAvailable(true);
        BookData.addBook(newBook);
        
        String borrowResult = borrowService.borrowBook("ISBN002", "USER001");
        assertEquals("Error: Cannot borrow - user has overdue books", borrowResult);
        assertTrue("New book should still be available", newBook.isAvailable());
    }
}
