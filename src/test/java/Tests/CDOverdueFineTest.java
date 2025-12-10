package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.User;
import library.entities.CD;
import library.data.UserData;
import library.services.CDService;
import java.time.LocalDate;

/**
 * Unit tests for CDService focusing on overdue fines.
 *
 * This test class validates the following scenario:
 * - Borrowing a CD and returning it after the due date
 * - Checking that the overdue fine is correctly applied to the user's account
 */
public class CDOverdueFineTest {

    private CDService cdService;
    private User testUser;
    private CD testCD;

    /**
     * Sets up the test environment before each test.
     * Clears all users, creates a test user and a test CD.
     */
    @Before
    public void setUp() {
        UserData.clearUsers();
        cdService = new CDService();

        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

        testCD = new CD("CD001", "Test Album", "Test Artist");
    }

    /**
     * Tests that returning a CD after its due date applies the correct overdue fine.
     *
     * Steps:
     * 1. Borrow a CD and simulate it being overdue by setting a past due date.
     * 2. Check that the user's fine balance is initially zero.
     * 3. Return the CD.
     * 4. Verify that the user's fine balance is updated with the overdue fine (20 NIS).
     */
    @Test
    public void testOverdueCDFine() {
        // Borrow the CD with a past due date to simulate overdue
        testUser.addBorrowedCD(testCD, LocalDate.now().minusDays(3));

        // Before return: fine balance should be 0
        assertEquals(0.0, testUser.getFineBalance(), 0.001);

        // Return the CD
        String result = cdService.returnCD(testCD, "USER001");
        assertEquals("Success: CD returned", result);

        // After return: fine balance should be 20 NIS
        assertEquals(20.0, testUser.getFineBalance(), 0.001);
    }
}
