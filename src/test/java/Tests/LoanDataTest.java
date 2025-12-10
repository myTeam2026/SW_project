package Tests;

import library.data.LoanData;
import library.entities.Loan;
import library.entities.User;
import org.junit.*;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;

public class LoanDataTest {

    @Before
    public void setup(){
        LoanData.clearLoans();
        LoanData.addLoan(
                new Loan(
                        "L1",
                        new User("U1","Aya","a@a","1"),
                        LocalDate.now().minusDays(1),
                        null,
                        null
                )
        );
        LoanData.addLoan(
                new Loan(
                        "L2",
                        new User("U1","Aya","a@a","1"),
                        LocalDate.now().minusDays(2),
                        LocalDate.now(),
                        null
                )
        );
        LoanData.addLoan(
                new Loan(
                        "L3",
                        new User("U2","Sara","s@s","2"),
                        LocalDate.now(),
                        null,
                        null
                )
        );
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<LoanData> c = LoanData.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuildList(){
        assertNotNull(LoanData.buildList());
    }

    @Test
    public void testSize(){
        assertEquals(3, LoanData.size());
    }

    @Test
    public void testExists(){
        assertTrue(LoanData.exists("L1"));
        assertFalse(LoanData.exists("ZZ"));
    }

    @Test
    public void testGetLoan(){
        Loan l = LoanData.getLoanById("L1");
        assertEquals("L1", l.getLoanId());
    }

    @Test
    public void testGetLoansByUser(){
        List<Loan> list = LoanData.getLoansByUser("U1");
        assertEquals(1, list.size());
    }

    @Test
    public void testGetActive(){
        List<Loan> active = LoanData.getActiveLoans();
        assertEquals(2, active.size());
    }

    @Test
    public void testClear(){
        LoanData.clearLoans();
        assertEquals(0, LoanData.size());
    }
}
