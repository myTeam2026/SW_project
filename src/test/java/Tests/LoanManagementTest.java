package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;
import java.util.List;

public class LoanManagementTest {
    
    private User testUser;
    private Book testBook;
    
    @Before
    public void setUp() {
        // تنظيف البيانات قبل كل اختبار
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        
        // إنشاء مستخدم وكتاب تجريبي
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
        
        testBook = new Book("Test Book", "Test Author", "ISBN001");
        testBook.setAvailable(false); // غير متاح لأنه مستعار
        BookData.addBook(testBook);
    }
    
    @After
    public void tearDown() {
        // تنظيف البيانات بعد كل اختبار
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
    }
    
    @Test
    public void testDetectOverdueBook() {
        // Given - كتاب متأخر (تاريخ استحقاق من 7 أيام)
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        // When - جلب القروض والتحقق من التأخر
        List<Loan> userLoans = LoanData.getLoansByUser(testUser.getId());
        Loan loan = userLoans.get(0);
        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        
        // Then - يجب أن يكون متأخر
        assertTrue("Book should be overdue", isOverdue);
        assertTrue("Due date should be in past", loan.getDueDate().isBefore(LocalDate.now()));
    }
    
    @Test
    public void testNotOverdueBook() {
        // Given - كتاب غير متأخر (مستحق بعد أسبوع)
        Loan notOverdueLoan = new Loan(testBook, testUser, 
                                     LocalDate.now(), 
                                     LocalDate.now().plusDays(7));
        LoanData.addLoan(notOverdueLoan);
        
        // When - التحقق من التأخر
        List<Loan> userLoans = LoanData.getLoansByUser(testUser.getId());
        Loan loan = userLoans.get(0);
        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        
        // Then - يجب لا يكون متأخر
        assertFalse("Book should not be overdue", isOverdue);
        assertTrue("Due date should be in future", loan.getDueDate().isAfter(LocalDate.now()));
    }
    
    @Test
    public void testGetUserActiveLoans() {
        // Given - إضافة قروض للمستخدم
        Loan loan1 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan1);
        
        Book book2 = new Book("Second Book", "Author2", "ISBN002");
        book2.setAvailable(false);
        BookData.addBook(book2);
        Loan loan2 = new Loan(book2, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan2);
        
        // When - جلب القروض النشطة
        List<Loan> activeLoans = LoanData.getLoansByUser(testUser.getId());
        
        // Then - التحقق من القروض
        assertFalse("Should have active loans", activeLoans.isEmpty());
        assertEquals("Should have 2 active loans", 2, activeLoans.size());
    }
    
    @Test
    public void testLoanExactly28Days() {
        // Given - قرض لمدة 28 يوم بالضبط (مستحق اليوم)
        Loan loan = new Loan(testBook, testUser, 
                           LocalDate.now().minusDays(28), 
                           LocalDate.now());
        LoanData.addLoan(loan);
        
        // When - التحقق من التأخر
        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        
        // Then - يجب لا يكون متأخر (لأنه مستحق اليوم)
        assertFalse("Book should not be overdue on due date", isOverdue);
        assertTrue("Due date should be today", loan.getDueDate().isEqual(LocalDate.now()));
    }
    
    @Test
    public void testLoan29DaysOverdue() {
        // Given - قرض متأخر 29 يوم
        Loan loan = new Loan(testBook, testUser, 
                           LocalDate.now().minusDays(57), 
                           LocalDate.now().minusDays(29));
        LoanData.addLoan(loan);
        
        // When - التحقق من التأخر
        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        
        // Then - يجب أن يكون متأخر
        assertTrue("Book should be overdue", isOverdue);
        assertTrue("Due date should be in past", loan.getDueDate().isBefore(LocalDate.now()));
    }
    
    @Test
    public void testExtendLoanDueDate() {
        // Given - قرض حالي
        LocalDate originalDueDate = LocalDate.now().plusDays(14);
        Loan loan = new Loan(testBook, testUser, LocalDate.now(), originalDueDate);
        LoanData.addLoan(loan);
        
        // When - تمديد القرض 7 أيام
        LocalDate newDueDate = originalDueDate.plusDays(7);
        loan.setDueDate(newDueDate);
        
        // Then - التحقق من التاريخ الجديد
        assertEquals("New due date should be extended by 7 days", 
                     originalDueDate.plusDays(7), loan.getDueDate());
        assertTrue("New due date should be after original", 
                   loan.getDueDate().isAfter(originalDueDate));
    }
    
    @Test
    public void testGetActiveLoans() {
        // Given - إضافة قروض نشطة
        Loan activeLoan1 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        Loan activeLoan2 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(14));
        LoanData.addLoan(activeLoan1);
        LoanData.addLoan(activeLoan2);
        
        // When - جلب القروض النشطة
        List<Loan> activeLoans = LoanData.getActiveLoans();
        
        // Then - التحقق من القروض
        assertFalse("Should have active loans", activeLoans.isEmpty());
        assertEquals("Should have 2 active loans", 2, activeLoans.size());
        
        for (Loan loan : activeLoans) {
            assertNull("Active loan should not have return date", loan.getReturnDate());
        }
    }
    
    @Test
    public void testReturnBook() {
        // Given - قرض نشط
        Loan loan = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan);
        
        // When - إرجاع الكتاب
        loan.setReturnDate(LocalDate.now());
        
        // Then - التحقق من الإرجاع
        assertNotNull("Return date should be set", loan.getReturnDate());
        assertEquals("Return date should be today", LocalDate.now(), loan.getReturnDate());
        
        // التحقق من أن القرض لم يعد نشط
        List<Loan> activeLoans = LoanData.getLoansByUser(testUser.getId());
        assertTrue("Should have no active loans after return", activeLoans.isEmpty());
    }
    
    @Test
    public void testNoActiveLoansForUser() {
        // Given - لا توجد قروض للمستخدم
        
        // When - جلب القروض النشطة
        List<Loan> activeLoans = LoanData.getLoansByUser("NONEXISTENT_USER");
        
        // Then - يجب أن تكون القائمة فارغة
        assertTrue("Should have no active loans for non-existent user", activeLoans.isEmpty());
    }
    
    @Test
    public void testLoanCreation() {
        // Given - بيانات قرض جديدة
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(28);
        
        // When - إنشاء قرض
        Loan loan = new Loan(testBook, testUser, borrowDate, dueDate);
        LoanData.addLoan(loan);
        
        // Then - التحقق من بيانات القرض
        assertNotNull("Loan should be created", loan);
        assertEquals("Book should match", testBook, loan.getBook());
        assertEquals("User should match", testUser, loan.getUser());
        assertEquals("Borrow date should match", borrowDate, loan.getBorrowDate());
        assertEquals("Due date should match", dueDate, loan.getDueDate());
        assertNull("Return date should be null for new loan", loan.getReturnDate());
    }
}