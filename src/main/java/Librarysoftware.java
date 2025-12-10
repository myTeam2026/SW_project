



import services.EmailService;
import services.NotificationService;

public final class Librarysoftware {

    private Librarysoftware(){}

    public static boolean testEmail(EmailService service){
        return service.sendReminder("x@test.com","test","msg");
    }

    public static NotificationService buildNotification(EmailService service){
        return new NotificationService(service);
    }

    public static String statusMessage(boolean ok){
        return ok ? "OK" : "FAIL";
    }

    public static void main(String[] args) {
        EmailService emailService = new EmailService(new EmailService.SmtpEmailSender());
        boolean testSent = testEmail(emailService);
        if (testSent) System.out.println("OK");
        else System.out.println("FAIL");
        NotificationService notificationService = buildNotification(emailService);
    }
}
