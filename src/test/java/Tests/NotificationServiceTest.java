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

public class NotificationServiceTest {
    
    private EmailService emailService;
    private NotificationService notificationService;
    private User testUser;
    private Book testBook;
    
    @Before
    public void setUp() {
        emailService = new EmailService();
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
    
    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        emailService.clearSentEmails();
    }

    @Test
    public void testSendOverdueReminders_SingleOverdueBook() {
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        int remindersSent = notificationService.sendOverdueReminders();
        
        assertEquals(1, remindersSent);
        assertEquals(1, emailService.getSentEmailsCount());
        
        String sentEmail = emailService.getSentEmails().get(0);
        assertTrue(sentEmail.contains("You have 1 overdue book"));
        assertTrue(sentEmail.contains("john@email.com"));
    }

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
        
        // الآن البريد يُرسل مرة واحدة فقط للمستخدم بغض النظر عن عدد الكتب
        assertEquals(1, remindersSent);
        assertEquals(1, emailService.getSentEmailsCount());
        
        String sentEmail = emailService.getSentEmails().get(0);
        assertTrue(sentEmail.contains("2 overdue book"));
    }

    @Test
    public void testSendOverdueReminders_NoOverdueBooks() {
        Loan notOverdueLoan = new Loan(testBook, testUser, 
                                     LocalDate.now(), 
                                     LocalDate.now().plusDays(7));
        LoanData.addLoan(notOverdueLoan);
        
        int remindersSent = notificationService.sendOverdueReminders();
        
        assertEquals(0, remindersSent);
        assertEquals(0, emailService.getSentEmailsCount());
    }

    @Test
    public void testSendOverdueReminderToUser_Success() {
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        int remindersSent = notificationService.sendOverdueReminderToUser("USER001");
        
        assertEquals(1, remindersSent);
        assertEquals(1, emailService.getSentEmailsCount());
    }

    @Test
    public void testSendOverdueReminderToUser_NoOverdueBooks() {
        Loan notOverdueLoan = new Loan(testBook, testUser, 
                                     LocalDate.now(), 
                                     LocalDate.now().plusDays(7));
        LoanData.addLoan(notOverdueLoan);
        
        int remindersSent = notificationService.sendOverdueReminderToUser("USER001");
        
        assertEquals(0, remindersSent);
        assertEquals(0, emailService.getSentEmailsCount());
    }

    @Test
    public void testSendOverdueReminderToUser_UserNotFound() {
        int remindersSent = notificationService.sendOverdueReminderToUser("NONEXISTENT_USER");
        
        assertEquals(0, remindersSent);
        assertEquals(0, emailService.getSentEmailsCount());
    }

    @Test
    public void testMessageContentFormat() {
        Loan overdueLoan = new Loan(testBook, testUser, 
                                  LocalDate.now().minusDays(35), 
                                  LocalDate.now().minusDays(7));
        LoanData.addLoan(overdueLoan);
        
        notificationService.sendOverdueReminders();
        
        String sentEmail = emailService.getSentEmails().get(0);
        assertTrue(sentEmail.contains("You have 1 overdue book"));
        assertTrue(sentEmail.contains("avoid additional fines"));
    }
}
