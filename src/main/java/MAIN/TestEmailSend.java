package MAIN;


import services.EmailService;
import services.NotificationService;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class TestEmailSend {
    public static void main(String[] args) {
        // أنشئ الـ EmailService (إرسال حقيقي)
        EmailService emailService = new EmailService(); // يستخدم SmtpEmailSender

        // أنشئ NotificationService
        NotificationService notifier = new NotificationService(emailService);

        // أرسل تذكير لمستخدم محدد (تجريبي)
        int sent = notifier.sendOverdueReminderToUser("u001"); // ID المستخدم التجريبي
        System.out.println("عدد الرسائل المرسلة: " + sent);
    }
}
