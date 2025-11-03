
package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import services.UserService;
import services.BorrowService;


  
//import services.FineService;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;

public class AdvancedBorrowingIntegrationTest {

    private UserService userService;
    private BorrowService borrowService;
   // private FineService fineService;
    private User testUser;
    private User adminUser;

    @Before
    public void setUp() {
        userService = new UserService();
        borrowService = new BorrowService();
        //fineService = new FineService();

        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();

        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

        adminUser = new User("admin", "Admin User", "admin@library.com");
        UserData.addUser(adminUser);

        // إضافة كتب للاختبار
        Book book1 = new Book("Book 1", "Author 1", "ISBN001");
        Book book2 = new Book("Book 2", "Author 2", "ISBN002");
        Book book3 = new Book("Book 3", "Author 3", "ISBN003");
        Book book4 = new Book("Book 4", "Author 4", "ISBN004");

        book1.setAvailable(true);
        book2.setAvailable(true);
        book3.setAvailable(true);
        book4.setAvailable(true);

        BookData.addBook(book1);
        BookData.addBook(book2);
        BookData.addBook(book3);
        BookData.addBook(book4);
    }

    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
    }

    /*@Test
    public void testCompleteBorrowingRestrictionsScenario() {
        // استعارة 3 كتب (الحد الأقصى)
        String result1 = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Success: Book borrowed successfully", result1);

        String result2 = borrowService.borrowBook("ISBN002", "USER001");
        assertEquals("Success: Book borrowed successfully", result2);

        String result3 = borrowService.borrowBook("ISBN003", "USER001");
        assertEquals("Success: Book borrowed successfully", result3);

        // محاولة استعارة الكتاب الرابع (يتجاوز الحد)
        String result4 = borrowService.borrowBook("ISBN004", "USER001");
        assertEquals("Error: Cannot borrow - maximum limit reached", result4);

        // جعل أحد الكتب متأخر وإزالة الحد الأقصى أولاً
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        borrowService.returnBook(loan.getLoanId()); // إرجاع كتاب لتخطي الحد الأقصى

        loan.setDueDate(LocalDate.now().minusDays(5)); // جعله متأخر

        String result5 = borrowService.borrowBook("ISBN004", "USER001");
        assertEquals("Error: Cannot borrow - user has overdue books", result5);

        // إضافة غرامة وإزالة الكتاب المتأخر أولاً
        LoanData.clearLoans(); // مسح كل القروض (بما فيها المتأخرة)
        testUser.addFine(15.0);

        String result6 = borrowService.borrowBook("ISBN004", "USER001");
        assertEquals("Error: Cannot borrow - user has unpaid fines", result6);

        // التحقق من عدم إمكانية إلغاء التسجيل
        boolean canUnregister = userService.canUnregisterUser("USER001");
        assertFalse("Should not be able to unregister with restrictions", canUnregister);
    }*/
    @Test
public void testCompleteBorrowingRestrictionsScenario() {
    // استعارة 3 كتب (الحد الأقصى)
    String result1 = borrowService.borrowBook("ISBN001", "USER001");
    assertEquals("Success: Book borrowed successfully", result1);

    String result2 = borrowService.borrowBook("ISBN002", "USER001");
    assertEquals("Success: Book borrowed successfully", result2);

    String result3 = borrowService.borrowBook("ISBN003", "USER001");
    assertEquals("Success: Book borrowed successfully", result3);

    // محاولة استعارة الكتاب الرابع (يتجاوز الحد)
    String result4 = borrowService.borrowBook("ISBN004", "USER001");
    assertEquals("Error: Cannot borrow - maximum limit reached", result4);

    // جعل أحد الكتب متأخر دون إرجاعه
    Loan overdueLoan = LoanData.getLoansByUser("USER001").get(0);
    overdueLoan.setDueDate(LocalDate.now().minusDays(5)); // جعله متأخر

    // محاولة استعارة كتاب جديد يفشل بسبب الكتب المتأخرة
    String result5 = borrowService.borrowBook("ISBN004", "USER001");
    assertEquals("Error: Cannot borrow - user has overdue books", result5);

    // إضافة غرامة مع إزالة جميع القروض المتأخرة
    LoanData.clearLoans(); // إزالة جميع القروض لتجربة الغرامة
    testUser.addFine(15.0);

    String result6 = borrowService.borrowBook("ISBN004", "USER001");
    assertEquals("Error: Cannot borrow - user has unpaid fines", result6);

    // التحقق من عدم إمكانية إلغاء التسجيل أثناء القيود
    boolean canUnregister = userService.canUnregisterUser("USER001");
    assertFalse("Should not be able to unregister with restrictions", canUnregister);

    // إزالة القيود وتجربة إلغاء التسجيل
    testUser.setFineBalance(0);
    testUser.setCanBorrow(true);

    String result7 = userService.unregisterUser("USER001", "admin");
    assertEquals("Success: User unregistered successfully", result7);
    assertFalse("User should be inactive", testUser.isActive());
}


    @Test
    public void testErrorMessagesFormat() {
        // استخدام كتاب موجود في BookData بدلاً من TestBook
        Book overdueBook = BookData.getBookByISBN("ISBN001");
        overdueBook.setAvailable(false); // جعله مستعار

        Loan overdueLoan = new Loan(overdueBook, testUser,
                                    LocalDate.now().minusDays(35),
                                    LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);

        // Test 1: خطأ الاستعارة مع كتب متأخرة
        String borrowResult1 = borrowService.borrowBook("ISBN002", "USER001");
        assertTrue("Error message should start with 'Error:'", borrowResult1.startsWith("Error:"));
        assertTrue("Error message should contain 'overdue'", borrowResult1.contains("overdue"));

        // Test 2: خطأ الاستعارة مع غرامات
        LoanData.clearLoans();
        testUser.addFine(10.0);

        String borrowResult2 = borrowService.borrowBook("ISBN002", "USER001");
        assertTrue("Error message should start with 'Error:'", borrowResult2.startsWith("Error:"));
        assertTrue("Error message should contain 'unpaid'", borrowResult2.contains("unpaid"));

        // Test 3: خطأ إلغاء التسجيل بواسطة مستخدم عادي
        String unregisterResult = userService.unregisterUser("USER001", "USER002");
        assertTrue("Error message should start with 'Error:'", unregisterResult.startsWith("Error:"));
        assertTrue("Error message should contain 'administrators'", unregisterResult.contains("administrators"));

        // Test 4: نجاح إلغاء التسجيل بعد إزالة القيود
        testUser.setFineBalance(0);
        testUser.setCanBorrow(true);

        String successResult = userService.unregisterUser("USER001", "admin");
        assertTrue("Success message should start with 'Success:'", successResult.startsWith("Success:"));
    }
}

