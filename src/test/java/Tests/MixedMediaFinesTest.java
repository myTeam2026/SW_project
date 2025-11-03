package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import library.entities.User;
import library.entities.Book;
import library.entities.CD;
import library.entities.Loan;
import library.data.UserData;
import library.data.BookData;
import library.data.CDData;
import library.data.LoanData;
import services.MediaReportService;
import services.CDService;

import java.time.LocalDate;

public class MixedMediaFinesTest {

    private MediaReportService reportService;
    private CDService cdService;
    private User testUser;
    private Book testBook;
    private CD testCD;

    @Before
    public void setUp() {
        // إعادة تهيئة البيانات
        UserData.clearUsers();
        BookData.clearBooks();
        CDData.clearCDs();
        LoanData.clearLoans();

        reportService = new MediaReportService();
        cdService = new CDService();

        // إنشاء مستخدم
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

        // إنشاء كتاب متأخر
        testBook = new Book("Book 1", "Author 1", "ISBN001");
        testBook.setAvailable(false); // تم استعارتها مسبقًا
        BookData.addBook(testBook);

        // إضافة Loan للكتاب المتأخر
        LoanData.addLoan(new Loan(testBook, testUser, LocalDate.now().minusDays(5), LocalDate.now().minusDays(1)));

        // إنشاء CD متأخر
        testCD = new CD("CD001", "Test Album", "Test Artist");
        CDData.addCD(testCD);
        testUser.addBorrowedCD(testCD, LocalDate.now().minusDays(5)); // متأخرة
    }

    @Test
    public void testTotalFines() {
        double totalFines = reportService.getTotalFines("USER001");
        // 10 NIS للكتاب + 20 NIS للCD
        assertEquals("Total fines should be sum of books and CDs", 30.0, totalFines, 0.001);
    }

    @Test
    public void testDetailedFineReport() {
        String report = reportService.getDetailedFineReport("USER001");

        assertTrue(report.contains("Book: Book 1"));
        assertTrue(report.contains("CD: CD001"));
        assertTrue(report.contains("Total fines: 30.0"));
    }
}