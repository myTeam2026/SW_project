package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;

/**
 * Email service used for sending real emails (SMTP)
 * and for mocking in unit tests.
 */
public class EmailService {

    /** Stores sent email logs (for debugging and testing) */
    private final List<String> sentEmails = new ArrayList<>();

    /** Email sending strategy (real SMTP or mock) */
    private final EmailSender emailSender;

    /**
     * Default constructor → uses real Gmail SMTP.
     */
    public EmailService() {
        this.emailSender = new SmtpEmailSender();
    }

    /**
     * Constructor for injecting a custom sender (mock in tests).
     */
    public EmailService(EmailSender sender) {
        this.emailSender = sender;
    }

    /**
     * Sends a reminder email.
     */
    public boolean sendReminder(String userEmail, String subject, String message) {
        if (userEmail == null || userEmail.isEmpty() ||
            message == null || message.isEmpty()) {
            return false;
        }

        boolean sent = emailSender.sendEmail(userEmail, subject, message);

        if (sent) {
            String log = "To: " + userEmail + " | Subject: " + subject + " | Message: " + message;
            sentEmails.add(log);
            System.out.println("📧 Email sent → " + log);
        }

        return sent;
    }

    /** Returns list of sent emails */
    public List<String> getSentEmails() {
        return new ArrayList<>(sentEmails);
    }

    /** Returns number of sent emails */
    public int getSentEmailsCount() {
        return sentEmails.size();
    }

    /** Clears email log */
    public void clearSentEmails() {
        sentEmails.clear();
    }

    // -------------------------------------------------------------------------
    // EMAIL SENDER INTERFACES + IMPLEMENTATIONS
    // -------------------------------------------------------------------------

    /**
     * Email sender interface for real + mock.
     */
    public interface EmailSender {
        boolean sendEmail(String to, String subject, String body);
    }

    /**
     * REAL SMTP sender (Gmail)
     */
    public static class SmtpEmailSender implements EmailSender {

        private final String username = "hmsthntsh@gmail.com";
        private final String password = "ygur btts qncg dwfm";  // App Password

        private final String host = "smtp.gmail.com";
        private final int port = 587;

        @Override
        public boolean sendEmail(String to, String subject, String body) {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", port);

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                return true;

            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * MOCK sender (for unit tests only)
     */
    public static class MockEmailSender implements EmailSender {

        private final List<String> sent = new ArrayList<>();

        @Override
        public boolean sendEmail(String to, String subject, String body) {
            sent.add("TO:" + to + " | SUBJECT:" + subject + " | BODY:" + body);
            return true;
        }

        public List<String> getSent() {
            return new ArrayList<>(sent);
        }
    }
}
