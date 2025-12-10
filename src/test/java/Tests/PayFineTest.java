package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import library.entities.User;
import library.services.FineService;
import library.data.UserData;

/**
 * Unit tests for the FineService class and User fine handling.
 *
 * This test class verifies that users can pay fines correctly, partially or fully,
 * and that their borrowing rights are updated accordingly. It also tests adding fines
 * and retrieving fine balances.
 */
public class PayFineTest {
    
    private FineService fineService;
    private User testUser;
    
    /**
     * Sets up test data before each test.
     * - Clears existing users.
     * - Initializes FineService.
     * - Adds a test user.
     */
    @Before
    public void setUp() {
        fineService = new FineService();
        UserData.clearUsers();
        
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
    }
    
    /**
     * Cleans up after each test by clearing all users.
     */
    @After
    public void tearDown() {
        UserData.clearUsers();
    }
    
    /**
     * Tests paying the full fine amount.
     * The user should have a fine balance of 0 and be allowed to borrow.
     */
    @Test
    public void testPayFullFine() {
        testUser.addFine(50.0);
        
        boolean paymentResult = fineService.payFine("USER001", 50.0);
        
        assertTrue("Payment should be successful", paymentResult);
        assertEquals("Fine balance should be 0", 0.0, testUser.getFineBalance(), 0.001);
        assertTrue("User should be able to borrow after full payment", testUser.canBorrow());
    }
    
    /**
     * Tests paying part of the fine.
     * The remaining balance should be correct and borrowing not allowed.
     */
    @Test
    public void testPayPartialFine() {
        testUser.addFine(50.0);
        
        boolean paymentResult = fineService.payFine("USER001", 30.0);
        
        assertTrue("Partial payment should be successful", paymentResult);
        assertEquals("Fine balance should be 20", 20.0, testUser.getFineBalance(), 0.001);
        assertFalse("User should not be able to borrow after partial payment", testUser.canBorrow());
    }
    
    /**
     * Tests attempting to pay more than the fine amount.
     * Should fail and leave the balance unchanged.
     */
    @Test
    public void testPayMoreThanFine() {
        testUser.addFine(50.0);
        
        boolean paymentResult = fineService.payFine("USER001", 60.0);
        
        assertFalse("Should not accept over-payment", paymentResult);
        assertEquals("Fine balance should remain 50", 50.0, testUser.getFineBalance(), 0.001);
    }
    
    /**
     * Tests attempting to pay zero.
     * Should fail and leave the balance unchanged.
     */
    @Test
    public void testPayZeroAmount() {
        testUser.addFine(50.0);
        
        boolean paymentResult = fineService.payFine("USER001", 0.0);
        
        assertFalse("Should not accept zero payment", paymentResult);
        assertEquals("Fine balance should remain 50", 50.0, testUser.getFineBalance(), 0.001);
    }
    
    /**
     * Tests adding a fine to a user.
     * The fine balance should be updated and borrowing not allowed.
     */
    @Test
    public void testAddFine() {
        fineService.addFine("USER001", 25.0);
        
        assertEquals("Fine balance should be 25", 25.0, testUser.getFineBalance(), 0.001);
        assertFalse("User should not be able to borrow with fine", testUser.canBorrow());
    }
    
    /**
     * Tests retrieving the fine balance for a user.
     */
    @Test
    public void testGetFineBalance() {
        testUser.addFine(15.0);
        
        double balance = fineService.getFineBalance("USER001");
        
        assertEquals("Should return correct fine balance", 15.0, balance, 0.001);
    }
    
    /**
     * Verifies that the user can borrow after paying the full fine.
     */
    @Test
    public void testUserBorrowingRightsAfterFullPayment() {
        testUser.addFine(50.0);
        fineService.payFine("USER001", 50.0);
        
        boolean canBorrow = testUser.canBorrow();
        
        assertTrue("User should be able to borrow after fine clearance", canBorrow);
    }
}
