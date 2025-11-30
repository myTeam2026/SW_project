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

    private BorrowCDService service;
    private User user;
    private CD cd;

    @Before
    public void setUp() {
        CDData.clearCDs();
        UserData.clearUsers();

        service = new BorrowCDService();

        user = new User("USER001", "John Doe", "john@email.com");
        user.setCanBorrow(true);
        UserData.addUser(user);

        cd = new CD("CD001", "Artist", "Title");
        cd.setAvailable(true);
        CDData.addCD(cd);
    }

    @Test
    public void testBorrowCDSuccess() {
        String result = service.borrowCD("CD001", user);

        assertEquals("Success: CD borrowed successfully", result);
        assertFalse(cd.isAvailable());
        assertTrue(user.hasBorrowedCD("CD001"));
    }

    @Test
    public void testBorrowCDNotFound() {
        String result = service.borrowCD("CD999", user);
        assertEquals("Error: CD not found", result);
    }

    @Test
    public void testBorrowUserNotFound() {
        String result = service.borrowCD("CD001", null);
        assertEquals("Error: User not found", result);
    }

    @Test
    public void testBorrowCDNotAvailable() {
        cd.setAvailable(false);
        String result = service.borrowCD("CD001", user);
        assertEquals("Error: CD is not available", result);
    }

    @Test
    public void testBorrowCDUserRestricted() {
        user.setCanBorrow(false);
        String result = service.borrowCD("CD001", user);
        assertEquals("Error: User cannot borrow due to restrictions", result);
    }

    @Test
    public void testReturnCDSuccess() {
        service.borrowCD("CD001", user);
        user.setBorrowedCDDueDate("CD001", LocalDate.now().plusDays(3));

        String result = service.returnCD("CD001", user);

        assertEquals("Success: CD returned successfully", result);
        assertTrue(cd.isAvailable());
        assertFalse(user.hasBorrowedCD("CD001"));
    }

    @Test
    public void testReturnCDNotFound() {
        String result = service.returnCD("CD999", user);
        assertEquals("Error: CD not found", result);
    }

    @Test
    public void testReturnCDNotBorrowed() {
        String result = service.returnCD("CD001", user);
        assertEquals("Error: User did not borrow this CD", result);
    }

    @Test
    public void testReturnCDLateAddsFine() {
        service.borrowCD("CD001", user);
        user.setBorrowedCDDueDate("CD001", LocalDate.now().minusDays(2));

        double beforeFine = user.getFineBalance();

        String result = service.returnCD("CD001", user);
        double afterFine = user.getFineBalance();

        assertEquals("Success: CD returned successfully", result);
        assertTrue(afterFine > beforeFine);
    }

    
    @Test
    public void testReturnCDOnTimeNoFine() {
        service.borrowCD("CD001", user);
        user.setBorrowedCDDueDate("CD001", LocalDate.now().plusDays(2));

        double beforeFine = user.getFineBalance();

        String result = service.returnCD("CD001", user);
        double afterFine = user.getFineBalance();

        assertEquals("Success: CD returned successfully", result);
        assertEquals(beforeFine, afterFine, 0.01);
    }
}
