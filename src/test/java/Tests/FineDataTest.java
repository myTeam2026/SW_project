package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import library.data.FineData;
import library.entities.Fine;
import library.entities.User;

import java.util.List;

public class FineDataTest {

    private User user1;
    private User user2;

    @Before
    public void setUp() {
        FineData.clearFines();
        user1 = new User("U001", "John Doe", "john@email.com");
        user2 = new User("U002", "Jane Doe", "jane@email.com");
    }

    @Test
    public void testCreateFineAndGetters() {
        Fine fine = FineData.createFine(user1, 10.0, "Late return");
        assertNotNull(fine);
        assertEquals("F001", fine.getFineId());
        assertEquals(user1, fine.getUser());
        assertEquals(10.0, fine.getAmount(), 0.01);
        assertEquals(10.0, user1.getFineBalance(), 0.01);
    }

    @Test
    public void testMultipleFinesIncrementIds() {
        Fine f1 = FineData.createFine(user1, 5.0, "Lost book");
        Fine f2 = FineData.createFine(user2, 7.0, "Damage");
        assertEquals("F001", f1.getFineId());
        assertEquals("F002", f2.getFineId());
    }

    @Test
    public void testGetFinesByUser() {
        Fine f1 = FineData.createFine(user1, 10.0, "Late");
        Fine f2 = FineData.createFine(user2, 5.0, "Lost");
        List<Fine> user1Fines = FineData.getFinesByUser("U001");
        assertEquals(1, user1Fines.size());
        assertEquals(f1, user1Fines.get(0));
    }

    @Test
    public void testGetTotalFinesByUser() {
        FineData.createFine(user1, 10.0, "Late");
        FineData.createFine(user1, 5.0, "Damage");
        double total = FineData.getTotalFinesByUser("U001");
        assertEquals(15.0, total, 0.01);
    }

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
