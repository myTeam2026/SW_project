package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import services.BorrowService;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;

/**
 * Unit tests for BorrowService focusing on borrowing restrictions.
 * 
 * This test class validates the following scenarios:
 * - Successful borrowing of a book
 * - Borrowing with overdue books
 * - Borrowing with unpaid fines
 * - Borrowing with inactive user accounts
 * - Checking if user can borrow under various conditions
 */
public class BorrowRestrictionsTest {
    
    private BorrowService borrowService;
    private User testUser;
    private Book testBook;
    
    /**
     * Sets up the test environment before each test.
     * Clears all books, loans, and users, then creates a test user and book.
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
     * Cleans up the test environment after each test.
     * Clears all loans, books, and users.
     */
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
    }
    
    /**
     * Tests successful borrowing of a book.
     * Checks that the book is marked unavailable and a loan is created with correct due date.
     */
    @Test
    public void testBorrowBookSuccess() {
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Success: Book borrowed successfully", result);
        Book borrowedBook = BookData.getBookByISBN("ISBN001");
        assertFalse("Book should not be available", borrowedBook.isAvailable());
        
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        assertNotNull("Loan should be created", loan);
        assertEquals("Due date should be 28 days from today", LocalDate.now().plusDays(28), loan.getDueDate());
    }
    
    /**
     * Tests borrowing fails if the user has overdue books.
     */
    @Test
    public void testBorrowWithOverdueBooks() {
        Loan overdueLoan = new Loan(testBook, testUser, LocalDate.now().minusDays(35), LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        Book newBook = new Book("New Book", "New Author", "ISBN002");
        newBook.setAvailable(true);
        BookData.addBook(newBook);
        
        String result = borrowService.borrowBook("ISBN002", "USER001");
        
        assertEquals("Error: Cannot borrow - user has overdue books", result);
        assertTrue("Book should still be available", newBook.isAvailable());
    }
    
    /**
     * Tests borrowing fails if the user has unpaid fines.
     */
    @Test
    public void testBorrowWithUnpaidFines() {
        testUser.addFine(50.0);
        
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Error: Cannot borrow - user has unpaid fines", result);
        assertTrue("Book should still be available", testBook.isAvailable());
    }
    
    /**
     * Tests borrowing fails if the user account is inactive.
     */
    @Test
    public void testBorrowWithInactiveUser() {
        testUser.setActive(false);
        
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Error: User account is inactive", result);
        assertTrue("Book should still be available", testBook.isAvailable());
    }
    
    /**
     * Tests that hasOverdueBooks method correctly identifies overdue books.
     */
    @Test
    public void testHasOverdueBooks() {
        Loan overdueLoan = new Loan(testBook, testUser, LocalDate.now().minusDays(35), LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        boolean hasOverdue = borrowService.hasOverdueBooks("USER001");
        
        assertTrue("Should have overdue books", hasOverdue);
    }
    
    /**
     * Tests that hasUnpaidFines method correctly identifies unpaid fines.
     */
    @Test
    public void testHasUnpaidFines() {
        testUser.addFine(25.0);
        
        boolean hasFines = borrowService.hasUnpaidFines("USER001");
        
        assertTrue("Should have unpaid fines", hasFines);
    }
    
    /**
     * Tests that canUserBorrow method returns true when user has no restrictions.
     */
    @Test
    public void testCanUserBorrow() {
        boolean canBorrow = borrowService.canUserBorrow("USER001");
        
        assertTrue("User should be able to borrow", canBorrow);
    }
    
    /**
     * Tests that canUserBorrow method returns false when user has overdue books.
     */
    @Test
    public void testCannotBorrowWithOverdue() {
        Loan overdueLoan = new Loan(testBook, testUser, LocalDate.now().minusDays(35), LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        boolean canBorrow = borrowService.canUserBorrow("USER001");
        
        assertFalse("User should not be able to borrow with overdue books", canBorrow);
    }
}
