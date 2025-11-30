package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;

import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import services.BorrowService;
import services.FineService;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;

public class ServiceTest {
    
    private BorrowService borrowService;
    private FineService fineService;
    private User testUser;
    private Book testBook;
    
    @Before
    public void setUp() {
        borrowService = new BorrowService();
        fineService = new FineService();
        
        BookData.clearBooks();
        UserData.clearUsers();
        LoanData.clearLoans();
        
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
        
        testBook = new Book("Test Book", "Test Author", "ISBN001");
        testBook.setAvailable(true);
        BookData.addBook(testBook);
    }
    
    
    @After
    public void tearDown() {
        BookData.clearBooks();
        UserData.clearUsers();
        LoanData.clearLoans();
    }
    
    @Test
    public void testIntegratedBorrowAndFineScenario() {
        // When - استعارة كتاب
        String borrowResult = borrowService.borrowBook("ISBN001", "USER001");
        
        // Then - التحقق من الاستعارة
        assertEquals("Success: Book borrowed successfully", borrowResult);
        assertFalse("Book should not be available", testBook.isAvailable());
        
        // When - إضافة غرامة للمستخدم
        fineService.addFine("USER001", 25.0);
        
        // Then - التحقق من الغرامة
        assertFalse("User should not be able to borrow with fine", testUser.CanBorrow());
        
        // When - دفع الغرامة
        boolean paymentResult = fineService.payFine("USER001", 25.0);
        
        // Then - التحقق من إزالة الغرامة
        assertTrue("Payment should be successful", paymentResult);
        assertTrue("User should be able to borrow after fine payment", testUser.CanBorrow());
    }
    
    @Test
    public void testCannotBorrowWithActiveFine() {
        // Given - غرامة نشطة
        fineService.addFine("USER001", 10.0);
        
        // When - محاولة استعارة كتاب
        String borrowResult = borrowService.borrowBook("ISBN001", "USER001");
        
        // Then - يجب أن ترجع رسالة خطأ
        assertEquals("Error: Cannot borrow - user has unpaid fines", borrowResult);
        assertTrue("Book should still be available", testBook.isAvailable());
    }
    
    @Test
    public void testBorrowAfterFinePayment() {
        // Given - غرامة مدفوعة
        fineService.addFine("USER001", 15.0);
        fineService.payFine("USER001", 15.0);
        
        // When - محاولة استعارة كتاب
        String borrowResult = borrowService.borrowBook("ISBN001", "USER001");
        
        // Then - يجب أن تنجح الاستعارة
        assertEquals("Success: Book borrowed successfully", borrowResult);
        assertTrue("User should have borrowing rights", testUser.CanBorrow ());
    }
    
    @Test
    public void testReturnBookScenario() {
        // Given - استعارة كتاب
        borrowService.borrowBook("ISBN001", "USER001");
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        
        // When - إرجاع الكتاب
        boolean returnResult = borrowService.returnBook(loan.getLoanId());
        
        // Then - التحقق من النتائج
        assertTrue("Return should be successful", returnResult);
        assertTrue("Book should be available again", testBook.isAvailable());
        
        // When - محاولة استعارة نفس الكتاب مرة أخرى
        String borrowAgain = borrowService.borrowBook("ISBN001", "USER001");
        
        // Then - يجب أن تنجح الاستعارة
        assertEquals("Success: Book borrowed successfully", borrowAgain);
    }
    
    @Test
    public void testBorrowWithOverdueBooks() {
        // Given - كتاب متأخر
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        Book newBook = new Book("New Book", "New Author", "ISBN002");
        newBook.setAvailable(true);
        BookData.addBook(newBook);
        
        // When - محاولة استعارة كتاب جديد
        String borrowResult = borrowService.borrowBook("ISBN002", "USER001");
        
        // Then - يجب أن ترجع رسالة خطأ
        assertEquals("Error: Cannot borrow - user has overdue books", borrowResult);
        assertTrue("New book should still be available", newBook.isAvailable());
    }
}