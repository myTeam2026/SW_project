package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import services.UserService;
import services.BorrowService;
import services.FineService;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;

public class UserManagementTest {
    
    private UserService userService;
    private BorrowService borrowService;
    private FineService fineService;
    private User testUser;
    private User adminUser;
    private Book testBook;
    
    @Before
    public void setUp() {
        userService = new UserService();
        borrowService = new BorrowService();
        fineService = new FineService();
        
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
        
        adminUser = new User("admin", "Admin User", "admin@library.com");
        UserData.addUser(adminUser);
        
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
    public void testUnregisterUserByAdmin() {
        String result = userService.unregisterUser("USER001", "admin");
        
        assertEquals("Success: User unregistered successfully", result);
        assertFalse("User should be inactive", testUser.isActive());
    }
    
    @Test
    public void testUnregisterUserByNonAdmin() {
        User regularUser = new User("USER002", "Regular User", "user@email.com");
        UserData.addUser(regularUser);
        
        String result = userService.unregisterUser("USER001", "USER002");
        
        assertEquals("Error: Only administrators can unregister users", result);
        assertTrue("User should still be active", testUser.isActive());
    }
    
    @Test
    public void testUnregisterUserWithActiveLoans() {
        borrowService.borrowBook("ISBN001", "USER001");
        
        String result = userService.unregisterUser("USER001", "admin");
        
        assertEquals("Error: Cannot unregister - user has active loans", result);
        assertTrue("User should still be active", testUser.isActive());
    }
    
    @Test
    public void testUnregisterUserWithUnpaidFines() {
        testUser.addFine(30.0);
        
        String result = userService.unregisterUser("USER001", "admin");
        
        assertEquals("Error: Cannot unregister - user has unpaid fines", result);
        assertTrue("User should still be active", testUser.isActive());
    }
    
    @Test
    public void testUnregisterNonExistentUser() {
        String result = userService.unregisterUser("NONEXISTENT", "admin");
        
        assertEquals("Error: User not found", result);
    }
    
    @Test
    public void testCanUnregisterUser() {
        boolean canUnregister = userService.canUnregisterUser("USER001");
        
        assertTrue("Should be able to unregister user", canUnregister);
    }
    
    @Test
    public void testCannotUnregisterWithActiveLoans() {
        borrowService.borrowBook("ISBN001", "USER001");
        
        boolean canUnregister = userService.canUnregisterUser("USER001");
        
        assertFalse("Should not be able to unregister with active loans", canUnregister);
    }
    
    @Test
    public void testCannotUnregisterWithUnpaidFines() {
        testUser.addFine(25.0);
        
        boolean canUnregister = userService.canUnregisterUser("USER001");
        
        assertFalse("Should not be able to unregister with unpaid fines", canUnregister);
    }
    
    @Test
    public void testUnregisterAfterReturningBooksAndPayingFines() {
        borrowService.borrowBook("ISBN001", "USER001");
        testUser.addFine(20.0);
        
        boolean canUnregisterBefore = userService.canUnregisterUser("USER001");
        assertFalse("Should not be able to unregister initially", canUnregisterBefore);
        
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        borrowService.returnBook(loan.getLoanId());
        fineService.payFine("USER001", 20.0);
        
        boolean canUnregisterAfter = userService.canUnregisterUser("USER001");
        assertTrue("Should be able to unregister after clearing restrictions", canUnregisterAfter);
        
        String result = userService.unregisterUser("USER001", "admin");
        assertEquals("Success: User unregistered successfully", result);
        assertFalse("User should be inactive", testUser.isActive());
    }
}