package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.Fine;
import library.entities.User;

/**
 * Unit tests for the {@link Fine} class.
 * <p>
 * These tests verify the correct behavior of the Fine class, including
 * constructor, getters, and payment handling.
 * </p>
 */
public class FineTest {

    /** A test user associated with the fine. */
    private User testUser;

    /** The fine instance to be tested. */
    private Fine fine;

    /**
     * Sets up the test environment before each test.
     * <p>
     * Initializes a test user and a fine associated with that user.
     * </p>
     */
    @Before
    public void setUp() {
        testUser = new User("U001", "Test User", "test@email.com");
        fine = new Fine("F001", testUser, 50.0, "Late return");
    }

    /**
     * Tests the constructor and getter methods of the Fine class.
     * <p>
     * Verifies that all attributes are correctly initialized and retrieved.
     * </p>
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("F001", fine.getFineId());
        assertEquals(testUser, fine.getUser());
        assertEquals(50.0, fine.getAmount(), 0.01);
        assertEquals("Late return", fine.getReason());
        assertFalse(fine.isPaid());
    }

    /**
     * Tests that paying the fine reduces the amount correctly.
     * <p>
     * Verifies that the fine is marked as paid only when the amount reaches zero.
     * </p>
     */
    @Test
    public void testPayFineReducesAmount() {
        fine.payFine(20.0);
        assertEquals(30.0, fine.getAmount(), 0.01);
        assertFalse(fine.isPaid());

        fine.payFine(30.0);
        assertEquals(0.0, fine.getAmount(), 0.01);
        assertTrue(fine.isPaid());
    }

    /**
     * Tests overpayment of the fine.
     * <p>
     * Verifies that paying more than the fine amount sets the amount to zero
     * and marks the fine as paid.
     * </p>
     */
    @Test
    public void testPayFineOverPayment() {
        fine.payFine(100.0);
        assertEquals(0.0, fine.getAmount(), 0.01);
        assertTrue(fine.isPaid());
    }
}
