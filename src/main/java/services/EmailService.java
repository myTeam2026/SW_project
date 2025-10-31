package services;

import java.util.ArrayList;
import java.util.List;

public class EmailService {
    private List<String> sentEmails;
    
    public EmailService() {
        this.sentEmails = new ArrayList<>();
    }
    
    public boolean sendReminder(String userEmail, String message) {
        if (userEmail == null || userEmail.isEmpty() || message == null || message.isEmpty()) {
            return false;
        }
        
        String emailRecord = "To: " + userEmail + " | Message: " + message;
        sentEmails.add(emailRecord);
        
        System.out.println("📧 Email sent: " + emailRecord);
        return true;
    }
    
    public List<String> getSentEmails() {
        return new ArrayList<>(sentEmails);
    }
    
    public void clearSentEmails() {
        sentEmails.clear();
    }
    
    public int getSentEmailsCount() {
        return sentEmails.size();
    }
}