package Tests;

import org.junit.*;
import library.services.BorrowCDService;
import library.entities.*;
import library.data.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class BorrowCDServiceTest {

    BorrowCDService s;

    @Before
    public void setup(){
        s = new BorrowCDService();
        CDData.clearCDs();
        UserData.clearUsers();
        LoanData.clearLoans();

        CDData.addCD(new CD("1","A","T"));
        User u = new User("U","Aya","a","p");
        UserData.addUser(u);
    }

    @Test
    public void testBorrowNoUser(){
        assertTrue(s.borrowCD("1",null).contains("Error"));
    }

    @Test
    public void testBorrowNoCD(){
        assertTrue(s.borrowCD("99",UserData.getUserById("U")).contains("Error"));
    }

    @Test
    public void testBorrowUnavailable(){
        CD c = CDData.getCD("1");
        c.setAvailable(false);
        assertTrue(s.borrowCD("1",UserData.getUserById("U")).contains("Error"));
    }

    @Test
    public void testBorrowUserRestricted(){
        User u = UserData.getUserById("U");
        u.setCanBorrow(false);
        assertTrue(s.borrowCD("1",u).contains("Error"));
    }

    @Test
    public void testBorrowOk(){
        User u = UserData.getUserById("U");
        u.setCanBorrow(true);
        CD c = CDData.getCD("1");
        c.setAvailable(true);
        String r = s.borrowCD("1",u);
        assertTrue(r.contains("Success"));
    }

    @Test
    public void testReturnNoUser(){
        assertTrue(s.returnCD("1",null).contains("Error"));
    }

    @Test
    public void testReturnNoCD(){
        assertTrue(s.returnCD("99",UserData.getUserById("U")).contains("Error"));
    }

    @Test
    public void testReturnNotBorrowed(){
        User u = UserData.getUserById("U");
        u.setCanBorrow(true);
        assertTrue(s.returnCD("1",u).contains("Error"));
    }

    @Test
    public void testReturnLate(){
        User u = UserData.getUserById("U");
        u.setCanBorrow(true);

        CD c = CDData.getCD("1");
        u.addBorrowedCD(c, LocalDate.now().minusDays(10));
        c.setAvailable(false);

        String r = s.returnCD("1",u);
        assertTrue(r.contains("Success"));
        assertTrue(u.getFineBalance()>0);
    }

    @Test
    public void testReturnOk(){
        User u = UserData.getUserById("U");
        u.setCanBorrow(true);

        CD c = CDData.getCD("1");
        u.addBorrowedCD(c, LocalDate.now().plusDays(1));
        c.setAvailable(false);

        String r = s.returnCD("1",u);
        assertTrue(r.contains("Success"));
    }

    @Test
    public void testGetUserCDLoans(){
        LoanData.clearLoans();
        LoanData.addLoan(
                new Loan(
                        "L",
                        UserData.getUserById("U"),
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        null
                )
        );

        assertNotNull(s.getUserCDLoans("U"));
    }
}
