package services;

import java.util.ArrayList;
import java.util.List;

public class EmailService {

    private final List<String> sentEmails = new ArrayList<>();
    private final EmailSender emailSender;

    public EmailService(){
        this.emailSender = new MockEmailSender();
    }

    public EmailService(EmailSender sender){
        this.emailSender = sender;
    }

    public boolean sendReminder(String userEmail, String subject, String message){
        if(isInvalid(userEmail) || isInvalid(message)) return false;
        boolean sent = emailSender.sendEmail(userEmail, subject, message);
        if(sent) sentEmails.add(buildLog(userEmail,subject,message));
        return sent;
    }

    public boolean isInvalid(String v){
        return v==null || v.isEmpty();
    }

    public String buildLog(String to,String sub,String msg){
        return "To:"+to+"|Sub:"+sub+"|Msg:"+msg;
    }

    public List<String> getSentEmails(){
        return new ArrayList<>(sentEmails);
    }

    public int getSentEmailsCount(){
        return sentEmails.size();
    }

    public void clearSentEmails(){
        sentEmails.clear();
    }

    public interface EmailSender{
        boolean sendEmail(String to,String subject,String body);
    }

    public static class MockEmailSender implements EmailSender {

        private final List<String> sent = new ArrayList<>();

        @Override
        public boolean sendEmail(String to,String subject,String body){
            sent.add(to+subject+body);
            return true;
        }

        public List<String> getSent(){
            return new ArrayList<>(sent);
        }
    }
}
