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

/**
 * Unit tests for user management functionality in the library system.
 * <p>
 * This test class covers scenarios for:
 * <ul>
 *     <li>Unregistering users by administrators</li>
 *     <li>Restrictions on unregistering due to active loans or unpaid fines</li>
 *     <li>Permission checks for non-admin users</li>
 *     <li>Successful unregistration after clearing loans and fines</li>
 * </ul>
 * </p>
 * <p>
 * Uses JUnit 4 annotations for setup, teardown, and test execution.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class UserManagementTest {
    
    /** Service for user-related operations */
    private UserService userService;
    
    /** Service for borrowing and returning books */
    private BorrowService borrowService;
    
    /** Service for handling fines */
    private FineService fineService;
    
    /** Test user for the scenarios */
    private User testUser;
    
    /** Administrator user */
    private User adminUser;
    
    /** Test book to borrow and return */
    private Book testBook;
    
    // -------------------- Setup & Teardown --------------------
    
    /**
     * Initializes test environment before each test.
     * <p>
     * Clears existing data and creates fresh users and books for testing.
     * </p>
     */
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
    
    /**
     * Cleans up test environment after each test.
     * <p>
     * Clears all loans, books, and users to ensure isolation between tests.
     * </p>
     */
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
    }
    
    // -------------------- Test Cases --------------------
    
    /**
     * Tests that an administrator can successfully unregister a user.
     */
    @Test
    public void testUnregisterUserByAdmin() {
        String result = userService.unregisterUser("USER001", "admin");
        assertEquals("Success: User unregistered successfully", result);
        assertFalse("User should be inactive", testUser.isActive());
    }
    
    /**
     * Tests that a non-admin user cannot unregister another user.
     */
    @Test
    public void testUnregisterUserByNonAdmin() {
        User regularUser = new User("USER002", "Regular User", "user@email.com");
        UserData.addUser(regularUser);
        
        String result = userService.unregisterUser("USER001", "USER002");
        assertEquals("Error: Only administrators can unregister users", result);
        assertTrue("User should still be active", testUser.isActive());
    }
    
    /**
     * Tests that a user with active loans cannot be unregistered.
     */
    @Test
    public void testUnregisterUserWithActiveLoans() {
        borrowService.borrowBook("ISBN001", "USER001");
        String result = userService.unregisterUser("USER001", "admin");
        assertEquals("Error: Cannot unregister - user has active loans", result);
        assertTrue("User should still be active", testUser.isActive());
    }
    
    /**
     * Tests that a user with unpaid fines cannot be unregistered.
     */
    @Test
    public void testUnregisterUserWithUnpaidFines() {
        testUser.addFine(30.0);
        String result = userService.unregisterUser("USER001", "admin");
        assertEquals("Error: Cannot unregister - user has unpaid fines", result);
        assertTrue("User should still be active", testUser.isActive());
    }
    
    /**
     * Tests that attempting to unregister a non-existent user returns an error.
     */
    @Test
    public void testUnregisterNonExistentUser() {
        String result = userService.unregisterUser("NONEXISTENT", "admin");
        assertEquals("Error: User not found", result);
    }
    
    /**
     * Tests the canUnregisterUser method when the user is eligible for unregistration.
     */
    @Test
    public void testCanUnregisterUser() {
        boolean canUnregister = userService.canUnregisterUser("USER001");
        assertTrue("Should be able to unregister user", canUnregister);
    }
    
    /**
     * Tests that a user with active loans cannot be unregistered.
     */
    @Test
    public void testCannotUnregisterWithActiveLoans() {
        borrowService.borrowBook("ISBN001", "USER001");
        boolean canUnregister = userService.canUnregisterUser("USER001");
        assertFalse("Should not be able to unregister with active loans", canUnregister);
    }
    
    /**
     * Tests that a user with unpaid fines cannot be unregistered.
     */
    @Test
    public void testCannotUnregisterWithUnpaidFines() {
        testUser.addFine(25.0);
        boolean canUnregister = userService.canUnregisterUser("USER001");
        assertFalse("Should not be able to unregister with unpaid fines", canUnregister);
    }
    
    /**
     * Tests that a user can be unregistered successfully after returning all books and paying all fines.
     */
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
