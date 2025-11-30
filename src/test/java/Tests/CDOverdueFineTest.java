package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.User;
import library.entities.CD;
import library.data.UserData;
import services.CDService;
import java.time.LocalDate;

public class CDOverdueFineTest {

    private CDService cdService;
    private User testUser;
    private CD testCD;

    @Before
    public void setUp() {
        UserData.clearUsers();
        cdService = new CDService();

        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

      testCD = new CD("CD001", "Test Album", "Test Artist");

    }

    
    @Test
    public void testOverdueCDFine() {
        testUser.addBorrowedCD(testCD, LocalDate.now().minusDays(3));

        assertEquals(0.0, testUser.getFineBalance(), 0.001);

                String result = cdService.returnCD(testCD, "USER001");
        assertEquals("Success: CD returned", result);

        assertEquals(20.0, testUser.getFineBalance(), 0.001);
    }
}

