package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.data.CDData;
import library.entities.CD;
import java.util.List;

/**
 * Unit tests for {@link library.data.CDData}.
 * <p>
 * This class verifies that CDData correctly handles:
 * - Adding and retrieving CDs
 * - Removing CDs
 * - Retrieving all CDs
 * - Clearing all CDs
 * - Handling non-existent CD IDs
 * </p>
 *
 * @version 1.1
 * @author Hamsa
 */
public class CDDataTest {

    /** First CD instance used for testing. */
    private CD cd1;

    /** Second CD instance used for testing. */
    private CD cd2;

    /**
     * Initializes the test environment before each test:
     * - Clears any existing CDs in CDData
     * - Creates sample CD objects
     */
    @Before
    public void setUp() {
        CDData.clearCDs();
        cd1 = new CD("CD1", "Artist1", "CD001");
        cd2 = new CD("CD2", "Artist2", "CD002");
    }


    /**
     * Tests adding a CD and retrieving it by its ID.
     */


@Test
public void testAddAndGetCD() {
    CD cd = new CD("CD001", "My Album", "Hamsa");
    CDData.addCD(cd);

    CD result = CDData.getCD("CD001");

    assertNotNull(result);
    assertEquals("CD001", result.getCdId());
}


    /**
     * Tests retrieving a CD that does not exist.
     */
    @Test
    public void testGetCD_NotFound() {
        CD result = CDData.getCD("CD999");
        assertNull(result);
    }

    /**
     * Tests retrieving all CDs after adding multiple CDs.
     */
    @Test
    public void testGetAllCDs() {
        CDData.addCD(cd1);
        CDData.addCD(cd2);
        List<CD> cds = CDData.getAllCDs();
        assertEquals(2, cds.size());
    }

    /**
     * Tests removing a CD by its ID.
     */
    @Test
    public void testRemoveCD() {
        CDData.addCD(cd1);
        CDData.removeCD("CD001");
        assertNull(CDData.getCD("CD001"));
    }

    /**
     * Tests attempting to remove a CD that does not exist.
     */
    @Test
    public void testRemoveCD_NotFound() {
        CDData.clearCDs();
        CDData.removeCD ("CD999");
        assertEquals(0, CDData.getAllCDs().size());
    }

    /**
     * Tests clearing all CDs from the data store.
     */
    @Test
    public void testClearCDs() {
        CDData.addCD(cd1);
        CDData.addCD(cd2);
        CDData.clearCDs();
        assertEquals(0, CDData.getAllCDs().size());
    }
}
