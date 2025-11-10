package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.data.CDData;
import library.entities.CD;
import java.util.List;

public class CDDataTest {
    private CD cd1;
    private CD cd2;

    @Before
    public void setUp() {
        CDData.clearCDs();
        cd1 = new CD("CD1", "Artist1", "CD001");
        cd2 = new CD("CD2", "Artist2", "CD002");
    }

    @Test
    public void testAddAndGetCD() {
        CDData.addCD(cd1);
        CD result = CDData.getCD("CD001");
        assertNotNull(result);
        assertEquals("CD1", result.getTitle());
    }

    @Test
    public void testGetCD_NotFound() {
        CD result = CDData.getCD("CD999");
        assertNull(result);
    }

    @Test
    public void testGetAllCDs() {
        CDData.addCD(cd1);
        CDData.addCD(cd2);
        List<CD> cds = CDData.getAllCDs();
        assertEquals(2, cds.size());
    }

    @Test
    public void testRemoveCD() {
        CDData.addCD(cd1);
        CDData.removeCD("CD001");
        assertNull(CDData.getCD("CD001"));
    }

    @Test
    public void testRemoveCD_NotFound() {
        CDData.clearCDs();
        CDData.removeCD("CD999");
        assertEquals(0, CDData.getAllCDs().size());
    }

    @Test
    public void testClearCDs() {
        CDData.addCD(cd1);
        CDData.addCD(cd2);
        CDData.clearCDs();
        assertEquals(0, CDData.getAllCDs().size());
    }
}
