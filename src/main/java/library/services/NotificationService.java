package library.services;

import library.entities.Loan;
import library.entities.User;
import library.data.LoanData;
import library.data.UserData;

import java.time.LocalDate;
import java.util.List;

public class NotificationService {

    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Sends reminders to all users who have overdue loans (books or CDs).
     * @return number of reminders successfully sent
     */
    public int sendOverdueReminders() {
        int remindersSent = 0;

        // Loop over all users
        for (User user : UserData.getAllUsers()) {
            int overdueCount = countOverdueBooksForUser(user.getId());

            if (overdueCount > 0) {
                String subject = "Overdue Books Reminder";
                String msg = "You have " + overdueCount + " overdue book"
                        + (overdueCount > 1 ? "s" : "")
                        + ". Please return them soon to avoid additional fines.";

                boolean sent = emailService.sendReminder(user.getEmail(), subject, msg);
                if (sent) {
                    remindersSent++;
                }
            }
        }

        return remindersSent;
    }

    /**
     * Sends a reminder to a specific user by ID.
     * @param userId the ID of the user
     * @return 1 if reminder sent, 0 otherwise
     */
    public int sendOverdueReminderToUser(String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return 0;

        int overdueCount = countOverdueBooksForUser(userId);
        if (overdueCount == 0) return 0;

        String subject = "Overdue Books Reminder";
        String msg = "You have " + overdueCount + " overdue book"
                + (overdueCount > 1 ? "s" : "")
                + ". Please return them soon to avoid additional fines.";

        boolean sent = emailService.sendReminder(user.getEmail(), subject, msg);
        return sent ? 1 : 0;
    }

    /**
     * Counts the number of overdue loans for a specific user.
     * @param userId the ID of the user
     * @return number of overdue loans
     */
   private int countOverdueBooksForUser(String userId) {
    int count = 0;

    List<Loan> loans = LoanData.getLoansByUser(userId);
    LocalDate today = LocalDate.now();

    for (Loan loan : loans) {
        // استخدم getReturnDate بدلاً من isReturned
        if (loan.getReturnDate() == null && loan.getDueDate() != null && loan.getDueDate().isBefore(today)) {
            count++;
        }
    }

    return count;
}

}
