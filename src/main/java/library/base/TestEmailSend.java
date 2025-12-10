package library.base;

import java.util.logging.Level;
import java.util.logging.Logger;
import library.services.EmailService;
import library.services.NotificationService;

public class TestEmailSend {

    private static final Logger logger = Logger.getLogger(TestEmailSend.class.getName());

    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        NotificationService notifier = new NotificationService(emailService);

        int sent = notifier.sendOverdueReminderToUser("u001");

        logger.log(Level.INFO, "عدد الرسائل المرسلة: {0}", sent);
    }
}
