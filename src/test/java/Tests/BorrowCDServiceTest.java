package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import library.data.CDData;
import library.data.UserData;
import library.entities.CD;
import library.entities.User;
import services.BorrowCDService;
import java.time.LocalDate;

public class BorrowCDServiceTest {

    private BorrowCDService borrowCDService;
    private User user;
    private CD cd1;
    private CD cd2;

    @Before
    public void setUp() {
        CDData.clearCDs();
        UserData.clearUsers();
        borrowCDService = new BorrowCDService();

        user = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(user);

        cd1 = new CD("CD001", "Artist1", "Title1");
        cd2 = new CD("CD002", "Artist2", "Title2");
        cd1.setAvailable(true);
        cd2.setAvailable(true);

        CDData.addCD(cd1);
        CDData.addCD(cd2);
    }

    @Test
    public void testBorrowCD_Success() {
        String result = borrowCDService.borrowCD("CD001", user);
        assertTrue(result.startsWith("Success"));
        assertFalse(cd1.isAvailable());
        assertTrue(user.hasBorrowedCD("CD001"));
    }

    @Test
    public void testBorrowCD_NotFound() {
        String result = borrowCDService.borrowCD("CD999", user);
        assertTrue(result.startsWith("Error"));
        assertTrue(result.contains("not found"));
    }

    @Test
    public void testBorrowCD_NotAvailable() {
        cd1.setAvailable(false);
        String result = borrowCDService.borrowCD("CD001", user);
        assertTrue(result.startsWith("Error"));
        assertTrue(result.contains("not available"));
    }

    @Test
    public void testBorrowCD_UserRestricted() {
        user.setCanBorrow(false);
        String result = borrowCDService.borrowCD("CD001", user);
        assertTrue(result.startsWith("Error"));
        assertTrue(result.contains("restrictions"));
    }

    @Test
    public void testReturnCD_Success() {
        borrowCDService.borrowCD("CD001", user);
        String result = borrowCDService.returnCD("CD001", user);
        assertTrue(result.startsWith("Success"));
        assertTrue(cd1.isAvailable());
        assertFalse(user.hasBorrowedCD("CD001"));
    }

    @Test
    public void testReturnCD_NotFound() {
        String result = borrowCDService.returnCD("CD999", user);
        assertTrue(result.startsWith("Error"));
        assertTrue(result.contains("not found"));
    }

    @Test
    public void testReturnCD_NotBorrowed() {
        String result = borrowCDService.returnCD("CD001", user);
        assertTrue(result.startsWith("Error"));
        assertTrue(result.contains("did not borrow"));
    }

    @Test
    public void testReturnCD_LateAddsFine() {
        borrowCDService.borrowCD("CD001", user);
        user.getBorrowedCDs().put("CD001", LocalDate.now().minusDays(3));
        double beforeFine = user.getFineBalance();
        String result = borrowCDService.returnCD("CD001", user);
        double afterFine = user.getFineBalance();
        assertTrue(result.startsWith("Success"));
        assertTrue(afterFine > beforeFine);
    }
}
