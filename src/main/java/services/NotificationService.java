package services;

import library.data.LoanData;
import library.data.UserData;
import library.entities.Loan;
import library.entities.User;

import java.time.LocalDate;
import java.util.List;

public class NotificationService {

    private EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    // إرسال تذكيرات لجميع المستخدمين
    public int sendOverdueReminders() {
        int remindersSent = 0;

        for (User user : UserData.getAllUsers()) {
            int sent = sendOverdueReminderToUser(user.getId());
            if (sent > 0) {
                remindersSent++;
            }
        }
        return remindersSent;
    }
    

    // إرسال تذكير لمستخدم واحد فقط
    public int sendOverdueReminderToUser(String userId) {

        User user = UserData.getUserById(userId);
        if (user == null) return 0;

        List<Loan> loans = LoanData.getLoansByUser(userId);

        long overdueCount = loans.stream()
                .filter(l -> l.getDueDate().isBefore(LocalDate.now()))
                .count();

        if (overdueCount == 0) return 0;

        String message = "You have " + overdueCount + " overdue book"
                + (overdueCount > 1 ? "s" : "")
                + ". Please return them to avoid additional fines.";

        emailService.sendEmail(user.getEmail(), message, message);

        return 1; // يتم إرسال رسالة واحدة فقط لكل مستخدم
    }
}
