package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import library.data.FineData;
import library.entities.Fine;
import library.entities.User;

import java.util.List;

/**
 * Unit tests for {@link library.data.FineData}.
 * <p>
 * This class verifies the functionality for creating fines,
 * retrieving fines by user, calculating total fines, and clearing fines.
 * It also ensures that fine IDs increment correctly and user fine balances
 * are updated appropriately.
 * </p>
 *
 * @version 1.1
 * @author Hamsa
 */
public class FineDataTest {

    /** First test user */
    private User user1;

    /** Second test user */
    private User user2;

    /**
     * Initializes the test environment before each test:
     * - Clears all fines in FineData
     * - Creates sample users
     */
    @Before
    public void setUp() {
        FineData.clearFines();
        user1 = new User("U001", "John Doe", "john@email.com");
        user2 = new User("U002", "Jane Doe", "jane@email.com");
    }

    /**
     * Tests creating a fine and verifying its properties and user balance.
     */
    @Test
    public void testCreateFineAndGetters() {
        Fine fine = FineData.createFine(user1, 10.0, "Late return");
        assertNotNull(fine);
        assertEquals("F001", fine.getFineId());
        assertEquals(user1, fine.getUser());
        assertEquals(10.0, fine.getAmount(), 0.01);
        assertEquals(10.0, user1.getFineBalance(), 0.01);
    }

    /**
     * Tests that multiple fines increment IDs correctly.
     */
    @Test
    public void testMultipleFinesIncrementIds() {
        Fine f1 = FineData.createFine(user1, 5.0, "Lost book");
        Fine f2 = FineData.createFine(user2, 7.0, "Damage");
        assertEquals("F001", f1.getFineId());
        assertEquals("F002", f2.getFineId());
    }

    /**
     * Tests retrieving fines associated with a specific user.
     */
    @Test
    public void testGetFinesByUser() {
        Fine f1 = FineData.createFine(user1, 10.0, "Late");
        Fine f2 = FineData.createFine(user2, 5.0, "Lost");
        List<Fine> user1Fines = FineData.getFinesByUser("U001");
        assertEquals(1, user1Fines.size());
        assertEquals(f1, user1Fines.get(0));
    }

    /**
     * Tests calculating the total fines for a specific user.
     */
    @Test
    public void testGetTotalFinesByUser() {
        FineData.createFine(user1, 10.0, "Late");
        FineData.createFine(user1, 5.0, "Damage");
        double total = FineData.getTotalFinesByUser("U001");
        assertEquals(15.0, total, 0.01);
    }

    /**
     * Tests clearing all fines and resetting fine IDs.
     */
    @Test
    public void testClearFines() {
        FineData.createFine(user1, 5.0, "Lost");
        FineData.createFine(user1, 8.0, "Late");
        FineData.clearFines();
        assertEquals(0, FineData.getFinesByUser("U001").size());
        Fine newFine = FineData.createFine(user1, 3.0, "New");
        assertEquals("F001", newFine.getFineId());
    }
}
