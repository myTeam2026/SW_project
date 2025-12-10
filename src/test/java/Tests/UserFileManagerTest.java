package Tests;

import library.data.UserFileManager;
import library.entities.User;
import org.junit.*;
import java.lang.reflect.Constructor;
import java.nio.file.*;
import java.io.*;
import static org.junit.Assert.*;

public class UserFileManagerTest {

    private static final String FILE = "Files/users.txt";

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
        Constructor<UserFileManager> c = UserFileManager.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuildFilePath(){
        assertEquals("Files/users.txt", UserFileManager.buildFilePath());
    }

    @Test
    public void testExists(){
        assertFalse(UserFileManager.exists());
        UserFileManager.saveUser(new User("X","Y","Z","P"));
        assertTrue(UserFileManager.exists());
    }

    @Test
    public void testFormatUser() {
        User u = new User("1","Aya","a@a","123");
        assertEquals("1,Aya,a@a,123", UserFileManager.formatUser(u));
    }

    @Test
    public void testParseUser(){
        User u = UserFileManager.parseUser("2,Lina,l@l,456");
        assertEquals("2",u.getId());
        assertEquals("Lina",u.getName());
        assertEquals("l@l",u.getEmail());
        assertEquals("456",u.getPassword());
    }

    @Test
    public void testSaveUserCreatesFile() {
        UserFileManager.saveUser(new User("2","Lina","l@l","456"));
        assertTrue(Files.exists(Paths.get(FILE)));
    }

    @Test
    public void testSaveUserContent() throws Exception {
        UserFileManager.saveUser(new User("3","Sara","s@s","789"));
        String txt = Files.readString(Paths.get(FILE));
        assertTrue(txt.contains("3,Sara,s@s,789"));
    }
}
