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

/**
 * Unit tests for calculating fines for a user with mixed media (books and CDs).
 *
 * This test class verifies that fines for overdue books and CDs are correctly
 * calculated and reported by the system.
 */
public class MixedMediaFinesTest {

    private MediaReportService reportService;
    private CDService cdService;
    private User testUser;
    private Book testBook;
    private CD testCD;

    /**
     * Sets up test data before each test.
     *
     * - Clears all previous users, books, CDs, and loans.
     * - Adds a test user.
     * - Adds a borrowed book with overdue date.
     * - Adds a borrowed CD with overdue date.
     */
    @Before
    public void setUp() {
        UserData.clearUsers();
        BookData.clearBooks();
        CDData.clearCDs();
        LoanData.clearLoans();

        reportService = new MediaReportService();
        cdService = new CDService();

        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

        testBook = new Book("Book 1", "Author 1", "ISBN001");
        testBook.setAvailable(false); // الكتاب تم استعاره
        BookData.addBook(testBook);

        LoanData.addLoan(new Loan(testBook, testUser, LocalDate.now().minusDays(5), LocalDate.now().minusDays(1)));

        testCD = new CD("CD001", "Test Album", "Test Artist");
        CDData.addCD(testCD);
        testUser.addBorrowedCD(testCD, LocalDate.now().minusDays(5)); // CD متأخرة
    }

    /**
     * Tests that the total fines for a user are correctly calculated
     * including both overdue books and CDs.
     *
     * Expected fines:
     * - Book overdue: 10 NIS
     * - CD overdue: 20 NIS
     * Total: 30 NIS
     */
    @Test
    public void testTotalFines() {
        double totalFines = reportService.getTotalFines("USER001");
        assertEquals("Total fines should be sum of books and CDs", 30.0, totalFines, 0.001);
    }

    /**
     * Tests that the detailed fine report includes all overdue media
     * and the correct total fines.
     *
     * The report should mention:
     * - Book: Book 1
     * - CD: CD001
     * - Total fines: 30.0
     */
    @Test
    public void testDetailedFineReport() {
        String report = reportService.getDetailedFineReport("USER001");

        assertTrue(report.contains("Book: Book 1"));
        assertTrue(report.contains("CD: CD001"));
        assertTrue(report.contains("Total fines: 30.0"));
    }
}
