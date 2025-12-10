package Tests;

import org.junit.*;
import library.services.CDService;
import library.entities.CD;
import library.entities.User;
import library.data.CDData;
import library.data.UserData;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CDServiceTest {

    CDService s;

    @Before
    public void setup(){
        s = new CDService();
        CDData.clearCDs();
        UserData.clearUsers();

        CDData.addCD(new CD("1","A","X"));
        User u = new User("U","Aya","a","p");
        UserData.addUser(u);
    }

    @Test
    public void testAddExists(){
        String r = s.addCD("A","T","1");
        assertTrue(r.contains("Error"));
    }

    @Test
    public void testAddOk(){
        String r = s.addCD("T","T","2");
        assertTrue(r.contains("Success"));
    }

    @Test
    public void testSearch(){
        assertNotNull(s.searchCDById("1"));
    }

    @Test
    public void testRemoveNotFound(){
        assertTrue(s.removeCD("99").contains("Error"));
    }

    @Test
    public void testRemoveOk(){
        assertTrue(s.removeCD("1").contains("Success"));
    }

    @Test
    public void testBorrowNoUser(){
        assertTrue(s.borrowCD(new CD("Z","A","B"),"xx").contains("Error"));
    }

    @Test
    public void testBorrowRestrict(){
        UserData.getUserById("U").setCanBorrow(false);
        assertTrue(s.borrowCD(CDData.getCD("1"),"U").contains("Error"));
    }

    @Test
    public void testBorrowNotAvailable(){
        CD c = CDData.getCD("1");
        c.setAvailable(false);
        UserData.getUserById("U").setCanBorrow(true);
        assertTrue(s.borrowCD(c,"U").contains("Error"));
    }

    @Test
    public void testBorrowOk(){
        UserData.getUserById("U").setCanBorrow(true);
        CD c = CDData.getCD("1");
        c.setAvailable(true);
        String r = s.borrowCD(c,"U");
        assertTrue(r.contains("Success"));
    }

    @Test
    public void testReturnNoUser(){
        assertTrue(s.returnCD(new CD("1","A","X"),"XX").contains("Error"));
    }

    @Test
    public void testReturnOk(){
        User u = UserData.getUserById("U");
        u.setCanBorrow(true);
        CD c = CDData.getCD("1");
        s.borrowCD(c,"U");
        String r = s.returnCD(c,"U");
        assertTrue(r.contains("Success"));
    }

    @Test
    public void testApplyFine(){
        User u = UserData.getUserById("U");
        u.setCanBorrow(true);
        CD c = CDData.getCD("1");
        u.addBorrowedCD(c, LocalDate.now().minusDays(10));
        s.applyOverdueFine(c,"U");
        assertTrue(u.getFineBalance()>0);
    }
}
