package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.CD;
import library.data.CDData;
import services.CDService;

public class CDServiceTest {
    private CDService cdService;
    private CD cd1;

    @Before
    public void setUp() {
        cdService = new CDService();
        CDData.clearCDs();
        cd1 = new CD("CD Title", "Artist A", "CD001");
        cd1.setAvailable(true);
        CDData.addCD(cd1);
    }

    @Test
    public void testAddCD() {
        String result = cdService.addCD("New CD", "Artist B", "CD002");
        assertTrue(result.startsWith("Success"));
        assertNotNull(CDData.getCD("CD002"));
    }

    @Test
    public void testAddDuplicateCD() {
        cdService.addCD("Another CD", "Artist A", "CD001");
        String result = cdService.addCD("Another CD", "Artist A", "CD001");
        assertTrue(result.startsWith("Error"));
    }

    @Test
    public void testSearchCDById() {
        CD result = cdService.searchCDById("CD001");
        assertNotNull(result);
        assertEquals("CD Title", result.getTitle());
    }

    @Test
    public void testSearchCD_NotFound() {
        CD result = cdService.searchCDById("CD999");
        assertNull(result);
    }

    @Test
    public void testRemoveCD() {
        String result = cdService.removeCD("CD001");
        assertTrue(result.startsWith("Success"));
    }

    @Test
    public void testRemoveCD_NotFound() {
        String result = cdService.removeCD("CD999");
        assertTrue(result.startsWith("Error"));
    }
}
