package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import services.EmailService;
import services.NotificationService;
import services.BorrowService;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;
import java.util.List;

/**
 * Integration tests for the library's communication system.
 * <p>
 * This class tests the interaction between borrowing books, 
 * sending overdue notifications, and recording emails using EmailService.
 * All emails are mocked using EmailService.MockEmailSender, so no real emails are sent.
 * </p>
 * 
 * <p><b>Tested components:</b></p>
 * <ul>
 *     <li>{@link BorrowService} - borrowing a book and updating loan data</li>
 *     <li>{@link NotificationService} - sending overdue reminders</li>
 *     <li>{@link EmailService} - sending and recording emails (mocked)</li>
 *     <li>{@link LoanData}, {@link BookData}, {@link UserData} - in-memory storage for test isolation</li>
 * </ul>
 * 
 * <p>Each test method ensures a clean environment by clearing users, books, loans,
 * and sent emails before and after execution.</p>
 * 
 * @version 1.0
 * @author Hamsa
 */
public class CommunicationIntegrationTest {
    
    /** EmailService instance using a mock sender */
    private EmailService emailService;
    
    /** NotificationService instance for sending overdue reminders */
    private NotificationService notificationService;
    
    /** BorrowService instance for borrowing books */
    private BorrowService borrowService;
    
    /** Test user */
    private User testUser;
    
    /** Test book */
    private Book testBook;
    
    /** Mock email sender used for recording emails */
    private EmailService.MockEmailSender mockSender;
    
    /**
     * Sets up the test environment before each test.
     * <p>
     * Initializes EmailService with a MockEmailSender, NotificationService, and BorrowService.
     * Also creates a test user and a test book in the in-memory data stores.
     * </p>
     */
    @Before
    public void setUp() {
        mockSender = new EmailService.MockEmailSender();
        emailService = new EmailService(mockSender);
        notificationService = new NotificationService(emailService);
        borrowService = new BorrowService();
        
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        
        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);
        
        testBook = new Book("Test Book", "Test Author", "ISBN001");
        testBook.setAvailable(true);
        BookData.addBook(testBook);
    }
    
    /**
     * Cleans up the test environment after each test.
     * <p>
     * Clears all loans, books, users, and recorded emails to ensure test isolation.
     * </p>
     */
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        emailService.clearSentEmails();
        mockSender.getSent().clear();
    }
    
    /**
     * Tests the complete overdue scenario.
     * <p>
     * Steps performed:
     * <ol>
     *     <li>Borrow a book for the test user</li>
     *     <li>Manually set the due date to the past to simulate an overdue book</li>
     *     <li>Send overdue reminders using NotificationService</li>
     *     <li>Verify that the reminder email was sent and recorded correctly in the mock sender</li>
     * </ol>
     * </p>
     */
    @Test
    public void testCompleteOverdueScenario() {
        borrowService.borrowBook("ISBN001", "USER001");
        
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        loan.setDueDate(LocalDate.now().minusDays(10));
        
        int remindersSent = notificationService.sendOverdueReminders();
        
        // Verify one reminder was sent
        assertEquals(1, remindersSent);
        assertEquals(1, mockSender.getSent().size());
        
        String sentEmail = mockSender.getSent().get(0);
        assertTrue(sentEmail.contains("overdue book"));
        assertTrue(sentEmail.contains("john@email.com"));
    }
    
    /**
     * Tests the mock email server's recording functionality.
     * <p>
     * Sends multiple emails through the mock sender and verifies that:
     * <ul>
     *     <li>The number of emails sent matches expectations</li>
     *     <li>The recorded emails contain the correct recipient addresses</li>
     * </ul>
     * </p>
     */
    @Test
    public void testMockEmailServerRecording() {
        EmailService testService = new EmailService(mockSender);
        testService.sendReminder("user1@test.com", "Reminder 1", "First reminder");
        testService.sendReminder("user2@test.com", "Reminder 2", "Second reminder");
        testService.sendReminder("user3@test.com", "Reminder 3", "Third reminder");

        // Verify three emails were sent
        assertEquals(3, mockSender.getSent().size());
        
        List<String> recordedEmails = mockSender.getSent();
        assertTrue(recordedEmails.get(0).contains("user1@test.com"));
        assertTrue(recordedEmails.get(1).contains("user2@test.com"));
        assertTrue(recordedEmails.get(2).contains("user3@test.com"));
    }
}
