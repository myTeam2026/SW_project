/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tests;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

import library.entities.CD;
import library.entities.User;
import library.data.CDData;
import services.BorrowCDService;

public class BorrowCDTest {

    private BorrowCDService borrowCDService;
    private User testUser;
    private CD testCD;

    @Before
    public void setUp() {
        borrowCDService = new BorrowCDService();
        CDData.clearCDs();

        testUser = new User("USER001", "John Doe", "john@email.com");
         testCD = new CD("CD001", "Greatest Hits", "Artist 1");

        
        CDData.addCD(testCD);
    }

    @After
    public void tearDown() {
        CDData.clearCDs();
    }
    @Test
    public void testBorrowCDSuccess() {
    String result = borrowCDService.borrowCD("CD001", testUser);
    
    // ✅ تأكدي فقط من أن النتيجة تبدأ بالنص الصحيح
    assertTrue("Message should indicate successful borrow",
        result.startsWith("Success: CD borrowed successfully"));
    
    assertFalse("CD should no longer be available", testCD.isAvailable());

    LocalDate dueDate = testUser.getBorrowedCDDueDate("CD001");
    assertEquals("Due date should be 7 days from today",
        LocalDate.now().plusDays(7), dueDate);
}

}
