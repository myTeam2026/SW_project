package Tests;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import services.EmailService;
import services.NotificationService;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;

import java.time.LocalDate;
import java.util.List;

/**
 * Unit tests for {@link NotificationService} using {@link EmailService.MockEmailSender}.
 * <p>
 * These tests verify sending overdue reminders to users with overdue books,
 * including single and multiple overdue books, users with no overdue books,
 * and correct formatting of reminder messages.
 * </p>
 */
public class NotificationServiceTest {

    /** The email service used for sending notifications. */
    private EmailService emailService;

    /** The notification service being tested. */
    private NotificationService notificationService;

    /** A test user for the notifications. */
    private User testUser;

    /** A test book for the notifications. */
    private Book testBook;

    /** The mock email sender to simulate sending emails. */
    private EmailService.MockEmailSender mockSender;

    /**
     * Sets up the test environment before each test.
     * <p>
     * Initializes MockEmailSender, EmailService, NotificationService,
     * and adds a test user and book to the data storage.
     * Clears previous loans, books, and users.
     * </p>
     */
    @Before
    public void setUp() {
        mockSender = new EmailService.MockEmailSender();
        emailService = new EmailService(mockSender);

        notificationService = new NotificationService(emailService);

        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();

        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

        testBook = new Book("Test Book", "Test Author", "ISBN001");
        testBook.setAvailable(false);
        BookData.addBook(testBook);
    }

    /**
     * Cleans up the test environment after each test.
     * <p>
     * Clears loans, books, users, and sent emails in the mock sender.
     * </p>
     */
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        mockSender.getSent().clear();
    }

    /**
     * Tests sending overdue reminders when the user has a single overdue book.
     */
    @Test
    public void testSendOverdueReminders_SingleOverdueBook() {
        Loan overdueLoan = new Loan(testBook, testUser,
                LocalDate.now().minusDays(35),
                LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);

        int remindersSent = notificationService.sendOverdueReminders();
        List<String> sentEmails = mockSender.getSent();

        assertEquals(1, remindersSent);
        assertEquals(1, sentEmails.size());
        assertTrue(sentEmails.get(0).contains("You have 1 overdue book"));
        assertTrue(sentEmails.get(0).contains("john@email.com"));
    }

    /**
     * Tests sending overdue reminders when the user has multiple overdue books.
     */
    @Test
    public void testSendOverdueReminders_MultipleOverdueBooks() {
        Loan overdueLoan1 = new Loan(testBook, testUser,
                LocalDate.now().minusDays(35),
                LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan1);

        Book book2 = new Book("Second Book", "Author2", "ISBN002");
        book2.setAvailable(false);
        BookData.addBook(book2);

        Loan overdueLoan2 = new Loan(book2, testUser,
                LocalDate.now().minusDays(40),
                LocalDate.now().minusDays(12));
        LoanData.addLoan(overdueLoan2);

        int remindersSent = notificationService.sendOverdueReminders();
        List<String> sentEmails = mockSender.getSent();

        assertEquals(1, remindersSent);
        assertEquals(1, sentEmails.size());
        assertTrue(sentEmails.get(0).contains("2 overdue books"));
    }

    /**
     * Tests sending overdue reminders when there are no overdue books.
     */
    @Test
    public void testSendOverdueReminders_NoOverdueBooks() {
        Loan notOverdueLoan = new Loan(testBook, testUser,
                LocalDate.now(),
                LocalDate.now().plusDays(7));
        LoanData.addLoan(notOverdueLoan);

        int remindersSent = notificationService.sendOverdueReminders();
        List<String> sentEmails = mockSender.getSent();

        assertEquals(0, remindersSent);
        assertEquals(0, sentEmails.size());
    }

    /**
     * Tests sending an overdue reminder to a specific user who has overdue books.
     */
    @Test
    public void testSendOverdueReminderToUser_Success() {
        Loan overdueLoan = new Loan(testBook, testUser,
                LocalDate.now().minusDays(35),
                LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);

        int remindersSent = notificationService.sendOverdueReminderToUser("USER001");
        List<String> sentEmails = mockSender.getSent();

        assertEquals(1, remindersSent);
        assertEquals(1, sentEmails.size());
        assertTrue(sentEmails.get(0).contains("You have 1 overdue book"));
    }

    /**
     * Tests sending an overdue reminder to a specific user who has no overdue books.
     */
    @Test
    public void testSendOverdueReminderToUser_NoOverdueBooks() {
        Loan notOverdueLoan = new Loan(testBook, testUser,
                LocalDate.now(),
                LocalDate.now().plusDays(7));
        LoanData.addLoan(notOverdueLoan);

        int remindersSent = notificationService.sendOverdueReminderToUser("USER001");
        List<String> sentEmails = mockSender.getSent();

        assertEquals(0, remindersSent);
        assertEquals(0, sentEmails.size());
    }

    /**
     * Tests sending an overdue reminder to a non-existent user.
     */
    @Test
    public void testSendOverdueReminderToUser_UserNotFound() {
        int remindersSent = notificationService.sendOverdueReminderToUser("NONEXISTENT_USER");
        List<String> sentEmails = mockSender.getSent();

        assertEquals(0, remindersSent);
        assertEquals(0, sentEmails.size());
    }

    /**
     * Tests the content format of the sent email messages.
     * <p>
     * Ensures the reminder email contains the correct number of overdue books
     * and additional message instructions.
     * </p>
     */
    @Test
    public void testMessageContentFormat() {
        Loan overdueLoan = new Loan(testBook, testUser,
                LocalDate.now().minusDays(35),
                LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);

        notificationService.sendOverdueReminders();
        List<String> sentEmails = mockSender.getSent();

        assertEquals(1, sentEmails.size());
        assertTrue(sentEmails.get(0).contains("You have 1 overdue book"));
        assertTrue(sentEmails.get(0).contains("avoid additional fines"));
    }
}
