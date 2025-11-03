/*package services;

import library.entities.User;
import library.entities.Loan;
import library.data.LoanData;
import library.data.UserData;
import java.time.LocalDate;
import java.util.List;

public class NotificationService {
    private EmailService emailService;
    
    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    public int sendOverdueReminders() {
        int remindersSent = 0;
        List<Loan> activeLoans = LoanData.getActiveLoans();
        
        for (Loan loan : activeLoans) {
            if (isOverdue(loan)) {
                User user = loan.getUser();
                int overdueCount = countUserOverdueBooks(user.getId());
                
                String message = "You have " + overdueCount + " overdue book(s). Please return them as soon as possible to avoid additional fines.";
                
                boolean sent = emailService.sendReminder(user.getEmail(), message);
                if (sent) {
                    remindersSent++;
                }
            }
        }
        
        return remindersSent;
    }
    
    public int sendOverdueReminderToUser(String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) {
            return 0;
        }
        
        int overdueCount = countUserOverdueBooks(userId);
        if (overdueCount > 0) {
            String message = "You have " + overdueCount + " overdue book(s). Please return them as soon as possible to avoid additional fines.";
            
            boolean sent = emailService.sendReminder(user.getEmail(), message);
            return sent ? 1 : 0;
        }
        
        return 0;
    }
    
    private boolean isOverdue(Loan loan) {
        return loan.getDueDate().isBefore(LocalDate.now()) && loan.getReturnDate() == null;
    }
    
    private int countUserOverdueBooks(String userId) {
        int count = 0;
        List<Loan> userLoans = LoanData.getLoansByUser(userId);
        
        for (Loan loan : userLoans) {
            if (isOverdue(loan)) {
                count++;
            }
        }
        
        return count;
    }
}*/
package services;

import library.data.UserData;
import library.data.LoanData;
import library.entities.User;
import library.entities.Loan;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    // إرسال تذكيرات لجميع المستخدمين
    public int sendOverdueReminders() {
        int remindersSent = 0;
        List<User> allUsers = UserData.getAllUsers();

        for (User user : allUsers) {
            List<Loan> allLoans = LoanData.getLoansByUser(user.getId());
            List<Loan> overdueLoans = new ArrayList<>();

            for (Loan loan : allLoans) {
                if (loan.getReturnDate() == null && loan.getDueDate().isBefore(java.time.LocalDate.now())) {
                    overdueLoans.add(loan);
                }
            }

            if (!overdueLoans.isEmpty()) {
                int count = overdueLoans.size();
                String message = "You have " + count + " overdue book(s). Please return them to avoid additional fines.";
                emailService.sendReminder(user.getEmail(), "Overdue Books Reminder", message);
                remindersSent++; // رسالة واحدة لكل مستخدم
            }
        }

        return remindersSent;
    }

    // إرسال تذكير لمستخدم محدد
    public int sendOverdueReminderToUser(String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return 0;

        List<Loan> allLoans = LoanData.getLoansByUser(userId);
        List<Loan> overdueLoans = new ArrayList<>();

        for (Loan loan : allLoans) {
            if (loan.getReturnDate() == null && loan.getDueDate().isBefore(java.time.LocalDate.now())) {
                overdueLoans.add(loan);
            }
        }

        if (overdueLoans.isEmpty()) return 0;

        int count = overdueLoans.size();
        String message = "You have " + count + " overdue book(s). Please return them to avoid additional fines.";
        emailService.sendReminder(user.getEmail(), "Overdue Books Reminder", message);

        return 1; // رسالة واحدة لكل مستخدم فقط
    }
}
