package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import services.EmailService;
import java.util.List;

public class EmailServiceTest {
    
    private EmailService emailService;
    
    @Before
    public void setUp() {
        emailService = new EmailService();
    }
    
    @After
    public void tearDown() {
        emailService.clearSentEmails();
    }
    
    @Test
    public void testSendReminderSuccess() {
        boolean result = emailService.sendReminder("user@email.com", "You have 2 overdue books.");
        
        assertTrue(result);
        assertEquals(1, emailService.getSentEmailsCount());
        
        String sentEmail = emailService.getSentEmails().get(0);
        assertTrue(sentEmail.contains("user@email.com"));
        assertTrue(sentEmail.contains("2 overdue books"));
    }
    
    @Test
    public void testSendReminderEmptyEmail() {
        boolean result = emailService.sendReminder("", "Test message");
        
        assertFalse(result);
        assertEquals(0, emailService.getSentEmailsCount());
    }
    
    @Test
    public void testSendReminderNullEmail() {
        boolean result = emailService.sendReminder(null, "Test message");
        
        assertFalse(result);
        assertEquals(0, emailService.getSentEmailsCount());
    }
    
    @Test
    public void testSendReminderEmptyMessage() {
        boolean result = emailService.sendReminder("user@email.com", "");
        
        assertFalse(result);
        assertEquals(0, emailService.getSentEmailsCount());
    }
    
    @Test
    public void testMultipleEmails() {
        emailService.sendReminder("user1@email.com", "First reminder");
        emailService.sendReminder("user2@email.com", "Second reminder");
        emailService.sendReminder("user3@email.com", "Third reminder");
        
        assertEquals(3, emailService.getSentEmailsCount());
        
        List<String> sentEmails = emailService.getSentEmails();
        assertTrue(sentEmails.get(0).contains("user1@email.com"));
        assertTrue(sentEmails.get(1).contains("user2@email.com"));
        assertTrue(sentEmails.get(2).contains("user3@email.com"));
    }
    
    @Test
    public void testClearSentEmails() {
        emailService.sendReminder("user@email.com", "Test message");
        assertEquals(1, emailService.getSentEmailsCount());
        
        emailService.clearSentEmails();
        
        assertEquals(0, emailService.getSentEmailsCount());
        assertTrue(emailService.getSentEmails().isEmpty());
    }
}