package services;

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
}