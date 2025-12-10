package Tests;

import org.junit.*;
import library.services.UserService;
import library.entities.User;
import library.entities.Loan;
import library.data.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService s;

    @Before
    public void setup(){
        s = new UserService();
        UserData.clearUsers();
        LoanData.clearLoans();

        UserData.addUser(new User("U","Aya","a","p"));
        UserData.addUser(new User("admin","Admin","a","x"));
    }

    @Test
    public void testNotAdmin(){
        String r = s.unregisterUser("U","someone");
        assertTrue(r.contains("Error"));
    }

    @Test
    public void testUserNotFound(){
        String r = s.unregisterUser("XX","admin");
        assertTrue(r.contains("Error"));
    }

    @Test
    public void testActiveLoans(){
        User u = UserData.getUserById("U");
        LoanData.addLoan(
                new Loan("L",u,LocalDate.now(),null,null)
        );
        String r = s.unregisterUser("U","admin");
        assertTrue(r.contains("Error"));
    }

    @Test
    public void testUnpaidFines(){
        User u = UserData.getUserById("U");
        u.addFine(10.0);
        String r = s.unregisterUser("U","admin");
        assertTrue(r.contains("Error"));
    }

    @Test
    public void testUnregisterOK(){
        String r = s.unregisterUser("U","admin");
        assertTrue(r.contains("Success"));
    }

    @Test
    public void testCanUnregisterFalse(){
        User u = UserData.getUserById("U");
        u.addFine(10.0);
        assertFalse(s.canUnregisterUser("U"));
    }

    @Test
    public void testCanUnregisterTrue(){
        assertTrue(s.canUnregisterUser("U"));
    }

    @Test
    public void testResetUserNotFound(){
        String r = s.resetPassword("XX","p","n");
        assertTrue(r.contains("Error"));
    }

    @Test
    public void testResetWrongOld(){
        String r = s.resetPassword("U","wrong","n");
        assertTrue(r.contains("Error"));
    }

    @Test
    public void testResetOk(){
        String r = s.resetPassword("U","p","newp");
        assertTrue(r.contains("Success"));
    }

}
