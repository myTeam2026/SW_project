package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.CD;
import library.data.CDData;
import services.CDService;

/**
 * Unit tests for {@link CDService} functionalities.
 * <p>
 * This test class verifies adding CDs, handling duplicates,
 * searching by ID, and removing CDs. It uses {@link CDData}
 * as an in-memory data store to isolate service logic.
 * </p>
 *
 * @version 1.1
 * author Hamsa
 */
public class CDServiceTest {

    /** Service instance used for testing. */
    private CDService cdService;

    /** Sample CD object used in multiple tests. */
    private CD cd1;

    /**
     * Initializes the test environment before each test.
     * <p>
     * Clears CDData, creates a new CDService instance,
     * and inserts one sample CD into the system.
     * </p>
     */
@Before
public void setUp() {
    cdService = new CDService();
    CDData.clearCDs();
    cd1 = new CD("CD001", "CD Title", "Artist A"); // <-- تصحيح ترتيب المعطيات
    cd1.setAvailable(true);
    CDData.addCD(cd1);
}


    /**
     * Tests that adding a new CD succeeds and is stored correctly.
     */
    @Test
    public void testAddCD() {
        String result = cdService.addCD("New CD", "Artist B", "CD002");
        assertTrue(result.startsWith("Success"));
        assertNotNull(CDData.getCD("CD002"));
    }

    /**
     * Tests that adding a CD with an existing ID results in an error.
     */
    @Test
    public void testAddDuplicateCD() {
        cdService.addCD("Another CD", "Artist A", "CD001");
        String result = cdService.addCD("Another CD", "Artist A", "CD001");
        assertTrue(result.startsWith("Error"));
    }

    /**
     * Tests searching for an existing CD by ID.
     */
    @Test
    public void testSearchCDById() {
        CD result = cdService.searchCDById("CD001");
        assertNotNull(result);
        assertEquals("CD Title", result.getTitle());
    }

    /**
     * Tests searching for a non-existing CD ID.
     */
    @Test
    public void testSearchCD_NotFound() {
        CD result = cdService.searchCDById("CD999");
        assertNull(result);
    }

    /**
     * Tests successful removal of an existing CD.
     */
    @Test
    public void testRemoveCD() {
        String result = cdService.removeCD("CD001");
        

        assertTrue(result.startsWith("Success"));
    }

    /**
     * Tests attempting to remove a CD that does not exist.
     */
    @Test
    public void testRemoveCD_NotFound() {
        String result = cdService.removeCD("CD999");
        assertTrue(result.startsWith("Error"));
    }
}
