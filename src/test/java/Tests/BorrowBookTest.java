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
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;

/**
 * Unit tests for BorrowService class focusing on book borrowing functionality.
 * 
 * This test class checks scenarios including:
 * - Successful borrowing of a book
 * - Borrowing an unavailable book
 * - Borrowing while having unpaid fines
 * - Checking user's ability to borrow
 * - Returning a book and verifying its state
 */
public class BorrowBookTest {
    
    private BorrowService borrowService;
    private User testUser;
    private Book testBook;
    
    /**
     * Sets up the test environment before each test case.
     * Initializes BorrowService, clears all BookData, UserData, and LoanData,
     * and creates a test user and test book.
     */
    @Before
    public void setUp() {
        borrowService = new BorrowService();
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
        
        testBook = new Book("Test Book", "Test Author", "ISBN001");
        testBook.setAvailable(true);
        BookData.addBook(testBook);
    }
    
    /**
     * Cleans up the test environment after each test case.
     * Clears all BookData, UserData, and LoanData.
     */
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
    }
    
    /**
     * Tests successful borrowing of a book.
     * Verifies:
     * - The borrow operation returns a success message
     * - The book availability is updated to false
     * - A loan record is created with correct due date
     */
    @Test
    public void testBorrowBookSuccess() {
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Success: Book borrowed successfully", result);
        
        Book borrowedBook = BookData.getBookByISBN("ISBN001");
        assertFalse("Book should be marked as not available", borrowedBook.isAvailable());
        
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        assertNotNull("Loan should be created", loan);
        assertEquals("Due date should be 28 days from today", 
                     LocalDate.now().plusDays(28), loan.getDueDate());
    }
    
    /**
     * Tests borrowing a book that is already unavailable.
     * Expects an error message indicating the book is not available.
     */
    @Test
    public void testBorrowUnavailableBook() {
        testBook.setAvailable(false);
        
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Error: Book is not available", result);
    }
    
    /**
     * Tests borrowing a book when the user has unpaid fines.
     * Expects an error message indicating unpaid fines.
     */
    @Test
    public void testBorrowWithFines() {
        testUser.addFine(50.0);
        
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Error: Cannot borrow - user has unpaid fines", result);
    }
    
    /**
     * Tests the canUserBorrow() method of BorrowService.
     * Verifies that a user without restrictions can borrow.
     */
    @Test
    public void testCanUserBorrow() {
        boolean canBorrow = borrowService.canUserBorrow("USER001");
        
        assertTrue("User should be able to borrow", canBorrow);
    }
    
    /**
     * Tests returning a borrowed book.
     * Verifies:
     * - Return operation is successful
     * - Book availability is restored
     * - Loan return date is set
     */
    @Test
    public void testReturnBook() {
        borrowService.borrowBook("ISBN001", "USER001");
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        
        boolean returnResult = borrowService.returnBook(loan.getLoanId());
        
        assertTrue("Return should be successful", returnResult);
        assertTrue("Book should be available again", testBook.isAvailable());
        assertNotNull("Return date should be set", loan.getReturnDate());
    }
}
