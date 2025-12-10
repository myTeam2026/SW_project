package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import services.EmailService;
import java.util.List;

/**
 * Unit tests for the {@link EmailService} class.
 * <p>
 * These tests use {@link EmailService.MockEmailSender} to simulate sending emails,
 * so no real emails are sent during testing.
 * </p>
 * <p>
 * The tests cover sending reminders with valid, empty, and null inputs, multiple emails,
 * and clearing the sent emails list.
 * </p>
 */
public class EmailServiceTest {

    /** The instance of EmailService used for testing. */
    private EmailService emailService;

    /** The mock email sender used to simulate sending emails. */
    private EmailService.MockEmailSender mockSender;

    /**
     * Sets up the test environment before each test.
     * <p>
     * Initializes the mock email sender and the email service using it.
     * </p>
     */
    @Before
    public void setUp() {
        mockSender = new EmailService.MockEmailSender();
        emailService = new EmailService(mockSender);
    }

    /**
     * Cleans up after each test by clearing the sent emails list.
     */
    @After
    public void tearDown() {
        emailService.clearSentEmails();
    }

    /**
     * Tests sending a reminder with valid recipient, subject, and message.
     * <p>
     * Verifies that the email is sent successfully and the sent email contains
     * the correct recipient, subject, and message.
     * </p>
     */
    @Test
    public void testSendReminderSuccess() {
        String recipient = "user@example.com";

        boolean result = emailService.sendReminder(
                recipient,
                "Overdue Reminder",
                "You have 2 overdue books."
        );

        assertTrue(result);
        assertEquals(1, emailService.getSentEmailsCount());

        String sentEmail = emailService.getSentEmails().get(0);
        assertTrue(sentEmail.contains(recipient));
        assertTrue(sentEmail.contains("Overdue Reminder"));
        assertTrue(sentEmail.contains("2 overdue books"));
    }

    /**
     * Tests sending a reminder with an empty recipient email.
     * <p>
     * Verifies that the email is not sent and the sent emails count remains zero.
     * </p>
     */
    @Test
    public void testSendReminderEmptyEmail() {
        boolean result = emailService.sendReminder(
                "",
                "Test Subject",
                "Test message"
        );
        assertFalse(result);
        assertEquals(0, emailService.getSentEmailsCount());
    }

    /**
     * Tests sending a reminder with a null recipient email.
     */
    @Test
    public void testSendReminderNullEmail() {
        boolean result = emailService.sendReminder(
                null,
                "Test Subject",
                "Test message"
        );
        assertFalse(result);
        assertEquals(0, emailService.getSentEmailsCount());
    }

    /**
     * Tests sending a reminder with an empty message.
     */
    @Test
    public void testSendReminderEmptyMessage() {
        boolean result = emailService.sendReminder(
                "user@example.com",
                "Test Subject",
                ""
        );
        assertFalse(result);
        assertEquals(0, emailService.getSentEmailsCount());
    }

    /**
     * Tests sending multiple reminders to different recipients.
     * <p>
     * Verifies that all emails are sent and each contains the correct recipient.
     * </p>
     */
    @Test
    public void testMultipleEmails() {
        emailService.sendReminder("a@mail.com", "S1", "M1");
        emailService.sendReminder("b@mail.com", "S2", "M2");
        emailService.sendReminder("c@mail.com", "S3", "M3");

        assertEquals(3, emailService.getSentEmailsCount());

        List<String> emails = emailService.getSentEmails();
        assertTrue(emails.get(0).contains("a@mail.com"));
        assertTrue(emails.get(1).contains("b@mail.com"));
        assertTrue(emails.get(2).contains("c@mail.com"));
    }

    /**
     * Tests clearing the list of sent emails.
     * <p>
     * Verifies that after clearing, the sent emails list is empty.
     * </p>
     */
    @Test
    public void testClearSentEmails() {
        emailService.sendReminder("x@mail.com", "Test", "Msg");
        assertEquals(1, emailService.getSentEmailsCount());

        emailService.clearSentEmails();
        assertEquals(0, emailService.getSentEmailsCount());
        assertTrue(emailService.getSentEmails().isEmpty());
    }
}
