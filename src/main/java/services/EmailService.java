

package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    // ======== MOCK EMAIL STORAGE FOR TESTS ========
    private final List<String> sentEmails = new ArrayList<>();

    public List<String> getSentEmails() {
        return sentEmails;
    }

    public int getSentEmailsCount() {
        return sentEmails.size();
    }

    public void clearSentEmails() {
        sentEmails.clear();
    }

    // ============================================================
    //                 SEND REAL EMAIL (GUI MODE)
    // ============================================================
    public boolean sendEmail(String to, String subject, String messageText) {

        // avoid null crash
        if (to == null || subject == null || messageText == null) {
            sentEmails.add("INVALID EMAIL PARAMS");
            return false;
        }

        // =========================
        //   MOCK MODE FOR TESTS
        // =========================
        if (!EmailConfig.ENABLE_REAL_EMAIL) {
            String msg = "TO: " + to + " | SUBJECT: " + subject + " | MSG: " + messageText;
            sentEmails.add(msg);
            return true;
        }

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            EmailConfig.USERNAME,
                            EmailConfig.APP_PASSWORD
                    );
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(EmailConfig.USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(messageText);

            Transport.send(msg);

            sentEmails.add("REAL EMAIL SENT TO: " + to);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    //     SEND REMINDER (2 PARAMETERS) — FOR OLD TESTS
    // =====================================================
    public boolean sendReminder(String email, String message) {

        if (email == null || email.isEmpty() ||
            message == null || message.isEmpty()) {

            sentEmails.add("INVALID REMINDER");
            return false;
        }

        return sendEmail(email, "Overdue Reminder", message);
    }


    // =====================================================
    //     SEND REMINDER (3 PARAMETERS) — FOR NEW TESTS
    // =====================================================
    public boolean sendReminder(String to, String subject, String messageText, boolean extra) {
        // This version is required because tests call sendReminder(to, subject, msg)
        return sendEmail(to, subject, messageText);
    }

    // نسخة 3 باراميتر بدون extra (Overload)
    public boolean sendReminder(String to, String subject, String messageText) {
        return sendEmail(to, subject, messageText);
    }
}
