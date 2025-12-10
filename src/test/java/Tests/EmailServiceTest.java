package Tests;

import org.junit.*;
import library.services.EmailService;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;

public class EmailServiceTest {

    static class FakeSender implements EmailService.EmailSender{
        @Override
        public boolean sendEmail(String t,String s,String b){
            return true;
        }
    }

    @Test
    public void testDefaultConstructor(){
        EmailService s = new EmailService();
        assertNotNull(s);
    }

    @Test
    public void testInjectedConstructor(){
        EmailService s = new EmailService(new FakeSender());
        assertNotNull(s);
    }

    @Test
    public void testInvalidUser(){
        EmailService s = new EmailService(new FakeSender());
        assertFalse(s.sendReminder("", "s", "m"));
        assertFalse(s.sendReminder(null, "s", "m"));
    }

    @Test
    public void testInvalidMessage(){
        EmailService s = new EmailService(new FakeSender());
        assertFalse(s.sendReminder("x", "s", ""));
        assertFalse(s.sendReminder("x", "s", null));
    }

    @Test
    public void testValidSend(){
        EmailService s = new EmailService(new FakeSender());
        assertTrue(s.sendReminder("x", "s", "m"));
        assertEquals(1, s.getSentEmailsCount());
    }

    @Test
    public void testClear(){
        EmailService s = new EmailService(new FakeSender());
        s.sendReminder("x","s","m");
        s.clearSentEmails();
        assertEquals(0,s.getSentEmailsCount());
    }

    @Test
    public void testBuildLog(){
        EmailService s = new EmailService();
        String l = s.buildLog("a","b","c");
        assertTrue(l.contains("a"));
    }

    @Test
    public void testGetSentEmails(){
        EmailService s = new EmailService(new FakeSender());
        s.sendReminder("1","2","3");
        assertEquals(1,s.getSentEmails().size());
    }

    @Test
    public void testMockClass(){
        EmailService.MockEmailSender m = new EmailService.MockEmailSender();
        assertTrue(m.sendEmail("a","b","c"));
        assertEquals(1,m.getSent().size());
    }

    @Test
    public void testPrivateDataAccess(){
        EmailService s = new EmailService(new FakeSender());
        s.sendReminder("a","b","c");
        assertTrue(s.getSentEmails().get(0).contains("a"));
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<EmailService> c = EmailService.class.getDeclaredConstructor();
        c.setAccessible(true);
        EmailService s = c.newInstance();
        assertNotNull(s);
    }
}
