package library.MAIN;

import library.services.EmailService;
import library.services.NotificationService;
import java.util.logging.Logger;

public class TestEmailSend {

    // تعريف Logger على مستوى الكلاس
    private static final Logger logger = Logger.getLogger(TestEmailSend.class.getName());

    public static void main(String[] args) {
        // أنشئ الـ EmailService (إرسال حقيقي)
        EmailService emailService = new EmailService(); // يستخدم SmtpEmailSender

        // أنشئ NotificationService
        NotificationService notifier = new NotificationService(emailService);

        // أرسل تذكير لمستخدم محدد (تجريبي)
        int sent = notifier.sendOverdueReminderToUser("u001"); // ID المستخدم التجريبي
        logger.info("عدد الرسائل المرسلة: " + sent);
    }
}
