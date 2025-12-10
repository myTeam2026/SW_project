package Tests;

import library.data.LogFileManager;
import org.junit.*;
import java.lang.reflect.Constructor;
import java.nio.file.*;
import java.io.*;
import static org.junit.Assert.*;

public class LogFileManagerTest {

    private static final String FILE = "Files/log.txt";

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
        Constructor<LogFileManager> c = LogFileManager.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuildFolder(){
        assertEquals("Files", LogFileManager.buildFolder());
    }

    @Test
    public void testBuildFilePath(){
        assertEquals("Files/log.txt", LogFileManager.buildFilePath());
    }

    @Test
    public void testFormat(){
        String result = LogFileManager.formatMessage("Hi","2025-10-10 10:10:10");
        assertEquals("[2025-10-10 10:10:10] Hi", result);
    }

    @Test
    public void testTimestamp(){
        String t = LogFileManager.currentTimestamp();
        assertTrue(t.length() > 5);
    }

    @Test
    public void testExists(){
        assertFalse(LogFileManager.exists());
        LogFileManager.log("X");
        assertTrue(LogFileManager.exists());
    }

    @Test
    public void testLogContent() throws Exception {
        LogFileManager.log("Hello");
        String txt = Files.readString(Paths.get(FILE));
        assertTrue(txt.contains("Hello"));
    }
}
