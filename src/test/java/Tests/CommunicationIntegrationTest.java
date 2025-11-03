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

public class CommunicationIntegrationTest {
    
    private EmailService emailService;
    private NotificationService notificationService;
    private BorrowService borrowService;
    private User testUser;
    private Book testBook;
    
    @Before
    public void setUp() {
        emailService = new EmailService();
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
    
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        emailService.clearSentEmails();
    }
    
    @Test
    public void testCompleteOverdueScenario() {
        borrowService.borrowBook("ISBN001", "USER001");
        
        Loan loan = LoanData.getLoansByUser("USER001").get(0);
        loan.setDueDate(LocalDate.now().minusDays(10));
        
        int remindersSent = notificationService.sendOverdueReminders();
        
        assertEquals(1, remindersSent);
        assertEquals(1, emailService.getSentEmailsCount());
        
        String sentEmail = emailService.getSentEmails().get(0);
        assertTrue(sentEmail.contains("overdue book"));
        assertTrue(sentEmail.contains("john@email.com"));
    }
    
    @Test
    public void testMockEmailServerRecording() {
        EmailService mockEmailService = new EmailService();
        mockEmailService.sendReminder("user1@test.com", "Reminder 1", "First reminder");
    mockEmailService.sendReminder("user2@test.com", "Reminder 2", "Second reminder");
    mockEmailService.sendReminder("user3@test.com", "Reminder 3", "Third reminder");

        
        assertEquals(3, mockEmailService.getSentEmailsCount());
        
        List<String> recordedEmails = mockEmailService.getSentEmails();
        assertTrue(recordedEmails.get(0).contains("user1@test.com"));
        assertTrue(recordedEmails.get(1).contains("user2@test.com"));
        assertTrue(recordedEmails.get(2).contains("user3@test.com"));
    }
}