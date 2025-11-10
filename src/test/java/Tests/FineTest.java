package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.Fine;
import library.entities.User;

public class FineTest {

    private User testUser;
    private Fine fine;

    @Before
    public void setUp() {
        testUser = new User("U001", "Test User", "test@email.com");
        fine = new Fine("F001", testUser, 50.0, "Late return");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("F001", fine.getFineId());
        assertEquals(testUser, fine.getUser());
        assertEquals(50.0, fine.getAmount(), 0.01);
        assertEquals("Late return", fine.getReason());
        assertFalse(fine.isPaid()); // should be unpaid at creation
    }

    @Test
    public void testPayFineReducesAmount() {
        fine.payFine(20.0);
        assertEquals(30.0, fine.getAmount(), 0.01);
        assertFalse(fine.isPaid()); // still unpaid because not full

        fine.payFine(30.0);
        assertEquals(0.0, fine.getAmount(), 0.01);
        assertTrue(fine.isPaid()); // should now be marked as paid
    }

    @Test
    public void testPayFineOverPayment() {
        fine.payFine(100.0);
        assertEquals(0.0, fine.getAmount(), 0.01);
        assertTrue(fine.isPaid());
    }
}
