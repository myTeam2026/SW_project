package Tests;

import library.data.CDFileManager;
import library.entities.CD;
import org.junit.*;
import java.lang.reflect.Constructor;
import java.nio.file.*;
import java.io.*;
import java.util.List;
import static org.junit.Assert.*;

public class CDFileManagerTest {

    private static final String FILE = "Files/cds.txt";

    @Before
    public void setup() throws Exception {
        Files.createDirectories(Paths.get("Files"));
        Files.deleteIfExists(Paths.get(FILE));
    }

    @After
    public void cleanup() throws Exception {
        Files.deleteIfExists(Paths.get(FILE));
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<CDFileManager> c = CDFileManager.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuildFilePath(){
        assertEquals("Files/cds.txt", CDFileManager.buildFilePath());
    }

    @Test
    public void testExists(){
        assertFalse(CDFileManager.exists());
        CDFileManager.saveCDToFile(new CD("T","A","1"));
        assertTrue(CDFileManager.exists());
    }

    @Test
    public void testFormat(){
        String r = CDFileManager.formatCD(new CD("T","A","2"));
        assertEquals("T,A,2", r);
    }

    @Test
    public void testParse(){
        CD c = CDFileManager.parseCD("X,Y,3");
        assertEquals("X",c.getTitle());
        assertEquals("Y",c.getArtist());
        assertEquals("3",c.getCdId());
    }

    @Test
    public void testSave() throws Exception {
        CDFileManager.saveCDToFile(new CD("M","N","4"));
        String t = Files.readString(Paths.get(FILE));
        assertTrue(t.contains("M,N,4"));
    }

    @Test
    public void testLoadWhenNoFile(){
        List<CD> list = CDFileManager.loadCDsFromFile();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testLoadValid() throws Exception {
        Files.writeString(Paths.get(FILE),"A,B,5\n");
        List<CD> list = CDFileManager.loadCDsFromFile();
        assertEquals(1,list.size());
        assertEquals("A",list.get(0).getTitle());
    }
}
