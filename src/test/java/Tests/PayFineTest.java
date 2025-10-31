package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import library.entities.User;
import services.FineService;
import library.data.UserData;

public class PayFineTest {
    
    private FineService fineService;
    private User testUser;
    
    @Before
    public void setUp() {
        fineService = new FineService();
        UserData.clearUsers();
        
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
    }
    
    @After
    public void tearDown() {
        UserData.clearUsers();
    }
    
    @Test
    public void testPayFullFine() {
        // Given - مستخدم لديه غرامة
        testUser.addFine(50.0);
        double initialBalance = testUser.getFineBalance();
        
        // When - دفع كامل المبلغ
        boolean paymentResult = fineService.payFine("USER001", 50.0);
        
        // Then - التحقق من النتائج
        assertTrue("Payment should be successful", paymentResult);
        assertEquals("Fine balance should be 0", 0.0, testUser.getFineBalance(), 0.001);
        assertTrue("User should be able to borrow after full payment", testUser.canBorrow());
    }
    
    @Test
    public void testPayPartialFine() {
        // Given - مستخدم لديه غرامة
        testUser.addFine(50.0);
        
        // When - دفع جزء من المبلغ
        boolean paymentResult = fineService.payFine("USER001", 30.0);
        
        // Then - التحقق من النتائج
        assertTrue("Partial payment should be successful", paymentResult);
        assertEquals("Fine balance should be 20", 20.0, testUser.getFineBalance(), 0.001);
        assertFalse("User should not be able to borrow after partial payment", testUser.canBorrow());
    }
    
    @Test
    public void testPayMoreThanFine() {
        // Given - مستخدم لديه غرامة
        testUser.addFine(50.0);
        
        // When - دفع أكثر من المبلغ المستحق
        boolean paymentResult = fineService.payFine("USER001", 60.0);
        
        // Then - يجب أن يفشل الدفع
        assertFalse("Should not accept over-payment", paymentResult);
        assertEquals("Fine balance should remain 50", 50.0, testUser.getFineBalance(), 0.001);
    }
    
    @Test
    public void testPayZeroAmount() {
        // Given - مستخدم لديه غرامة
        testUser.addFine(50.0);
        
        // When - دفع 0 دولار
        boolean paymentResult = fineService.payFine("USER001", 0.0);
        
        // Then - يجب أن يفشل الدفع
        assertFalse("Should not accept zero payment", paymentResult);
        assertEquals("Fine balance should remain 50", 50.0, testUser.getFineBalance(), 0.001);
    }
    
    @Test
    public void testAddFine() {
        // When - إضافة غرامة
        fineService.addFine("USER001", 25.0);
        
        // Then - التحقق من الغرامة
        assertEquals("Fine balance should be 25", 25.0, testUser.getFineBalance(), 0.001);
        assertFalse("User should not be able to borrow with fine", testUser.canBorrow());
    }
    
    @Test
    public void testGetFineBalance() {
        // Given - إضافة غرامة
        testUser.addFine(15.0);
        
        // When - جلب رصيد الغرامات
        double balance = fineService.getFineBalance("USER001");
        
        // Then - التحقق من الرصيد
        assertEquals("Should return correct fine balance", 15.0, balance, 0.001);
    }
    
    @Test
    public void testUserBorrowingRightsAfterFullPayment() {
        // Given - دفع كامل الغرامة
        testUser.addFine(50.0);
        fineService.payFine("USER001", 50.0);
        
        // When - التحقق من صلاحية الاستعارة
        boolean canBorrow = testUser.canBorrow();
        
        // Then - يجب يكون مسموح بالاستعارة
        assertTrue("User should be able to borrow after fine clearance", canBorrow);
    }
}