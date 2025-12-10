
import services.EmailService;
import services.NotificationService;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Librarysoftware {

    public static void main(String[] args) {
        // إنشاء خدمة الإيميل الحقيقية باستخدام SMTP
        EmailService emailService = new EmailService(new EmailService.SmtpEmailSender());

        // --- رسالة تجريبية قبل إرسال التذكيرات لجميع المستخدمين ---
        boolean testSent = emailService.sendReminder(
            "hamsa.test@example.com", 
            "Test Reminder", 
            "This is a test message to confirm email sending."
        );

        if (testSent) {
            System.out.println("✅ Test email sent successfully!");
        } else {
            System.out.println("❌ Test email failed!");
        }

        // إنشاء خدمة الإشعارات
        NotificationService notificationService = new NotificationService(emailService);

//ygur btts qncg dwfm
    }
}
