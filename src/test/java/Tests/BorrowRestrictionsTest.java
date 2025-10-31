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

public class BorrowRestrictionsTest {
    
    private BorrowService borrowService;
    private User testUser;
    private Book testBook;
    
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
    
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
    }
    
    @Test
    public void testBorrowBookSuccess() {
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Success: Book borrowed successfully", result);
        
        Book borrowedBook = BookData.getBookByISBN("ISBN001");
        assertFalse("Book should not be available", borrowedBook.isAvailable());
        
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        assertNotNull("Loan should be created", loan);
        assertEquals("Due date should be 28 days from today", 
                     LocalDate.now().plusDays(28), loan.getDueDate());
    }
    
    @Test
    public void testBorrowWithOverdueBooks() {
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        Book newBook = new Book("New Book", "New Author", "ISBN002");
        newBook.setAvailable(true);
        BookData.addBook(newBook);
        
        String result = borrowService.borrowBook("ISBN002", "USER001");
        
        assertEquals("Error: Cannot borrow - user has overdue books", result);
        assertTrue("Book should still be available", newBook.isAvailable());
    }
    
    @Test
    public void testBorrowWithUnpaidFines() {
        testUser.addFine(50.0);
        
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Error: Cannot borrow - user has unpaid fines", result);
        assertTrue("Book should still be available", testBook.isAvailable());
    }
    
    @Test
    public void testBorrowWithInactiveUser() {
        testUser.setActive(false);
        
        String result = borrowService.borrowBook("ISBN001", "USER001");
        
        assertEquals("Error: User account is inactive", result);
        assertTrue("Book should still be available", testBook.isAvailable());
    }
    
    @Test
    public void testHasOverdueBooks() {
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        boolean hasOverdue = borrowService.hasOverdueBooks("USER001");
        
        assertTrue("Should have overdue books", hasOverdue);
    }
    
    @Test
    public void testHasUnpaidFines() {
        testUser.addFine(25.0);
        
        boolean hasFines = borrowService.hasUnpaidFines("USER001");
        
        assertTrue("Should have unpaid fines", hasFines);
    }
    
    @Test
    public void testCanUserBorrow() {
        boolean canBorrow = borrowService.canUserBorrow("USER001");
        
        assertTrue("User should be able to borrow", canBorrow);
    }
    
    @Test
    public void testCannotBorrowWithOverdue() {
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        boolean canBorrow = borrowService.canUserBorrow("USER001");
        
        assertFalse("User should not be able to borrow with overdue books", canBorrow);
    }
}