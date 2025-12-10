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

/**
 * Unit tests for {@link BorrowCDService}.
 * <p>
 * This test class ensures correct behavior for borrowing and returning CDs,
 * including availability checks, user permissions, late return fines,
 * and validation of CD/user existence.
 * </p>
 *
 * @version 1.1
 * author Hamsa
 */
public class BorrowCDServiceTest {

    /** Service under test */
    private BorrowCDService service;

    /** Example user instance */
    private User user;

    /** Example CD instance */
    private CD cd;

    /**
     * Initializes clean test data before each test.
     */
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

    /**
     * Tests successful CD borrowing.
     */
    @Test
    public void testBorrowCDSuccess() {
        String result = service.borrowCD("CD001", user);

        assertEquals("Success: CD borrowed successfully", result);
        assertFalse(cd.isAvailable());
        assertTrue(user.hasBorrowedCD("CD001"));
    }

    /**
     * Tests borrowing a CD that does not exist.
     */
    @Test
    public void testBorrowCDNotFound() {
        String result = service.borrowCD("CD999", user);
        assertEquals("Error: CD not found", result);
    }

    /**
     * Tests borrowing with a null user reference.
     */
    @Test
    public void testBorrowUserNotFound() {
        String result = service.borrowCD("CD001", null);
        assertEquals("Error: User not found", result);
    }

    /**
     * Tests borrowing a CD that is not available.
     */
    @Test
    public void testBorrowCDNotAvailable() {
        cd.setAvailable(false);
        String result = service.borrowCD("CD001", user);
        assertEquals("Error: CD is not available", result);
    }

    /**
     * Tests borrowing when the user is restricted from borrowing.
     */
    @Test
    public void testBorrowCDUserRestricted() {
        user.setCanBorrow(false);
        String result = service.borrowCD("CD001", user);
        assertEquals("Error: User cannot borrow due to restrictions", result);
    }

    /**
     * Tests successful CD return.
     */
    @Test
    public void testReturnCDSuccess() {
        service.borrowCD("CD001", user);
        user.setBorrowedCDDueDate("CD001", LocalDate.now().plusDays(3));

        String result = service.returnCD("CD001", user);

        assertEquals("Success: CD returned successfully", result);
        assertTrue(cd.isAvailable());
        assertFalse(user.hasBorrowedCD("CD001"));
    }

    /**
     * Tests returning a CD that does not exist.
     */
    @Test
    public void testReturnCDNotFound() {
        String result = service.returnCD("CD999", user);
        assertEquals("Error: CD not found", result);
    }

    /**
     * Tests returning a CD the user never borrowed.
     */
    @Test
    public void testReturnCDNotBorrowed() {
        String result = service.returnCD("CD001", user);
        assertEquals("Error: User did not borrow this CD", result);
    }

    /**
     * Tests that a late return adds a fine to the user account.
     */
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

    /**
     * Tests that returning a CD on time does not add a fine.
     */
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
