package Tests;

import library.entities.CDLoan;
import org.junit.*;
import java.lang.reflect.Constructor;
import static org.junit.Assert.*;

public class CDLoanTest {

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<CDLoan> c = CDLoan.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuild(){
        CDLoan l = CDLoan.build("1","U","2025");
        assertEquals("1",l.getCdId());
        assertFalse(l.isReturned());
    }

    @Test
    public void testParse(){
        CDLoan l = CDLoan.parse("2,UU,2024,true");
        assertEquals("2",l.getCdId());
        assertTrue(l.isReturned());
    }

    @Test
    public void testFormat(){
        CDLoan l = new CDLoan("3","UUU","2023",false);
        String f = l.format();
        assertEquals("3,UUU,2023,false",f);
    }

    @Test
    public void testToggle(){
        CDLoan l = new CDLoan("4","X","2021",false);
        l.toggleReturned();
        assertTrue(l.isReturned());
    }

    @Test
    public void testSetBorrowDate(){
        CDLoan l = new CDLoan("5","X","X",false);
        l.setBorrowDate("Y");
        assertEquals("Y",l.getBorrowDate());
    }

    @Test
    public void testSetReturned(){
        CDLoan l = new CDLoan("6","A","B",false);
        l.setReturned(true);
        assertTrue(l.isReturned());
    }

    @Test
    public void testToString(){
        CDLoan l = new CDLoan("7","A","B",true);
        assertTrue(l.toString().contains("7"));
    }

    @Test
    public void testEquals(){
        CDLoan a = new CDLoan("A","U","D",false);
        CDLoan b = new CDLoan("A","Z","Y",true);
        assertEquals(a,b);
    }

    @Test
    public void testHashCode(){
        CDLoan a = new CDLoan("AA","U","D",false);
        assertEquals("AA".hashCode(), a.hashCode());
    }
}
