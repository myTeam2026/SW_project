package Tests;

import library.data.BookFileManager;
import library.entities.Book;
import org.junit.*;
import java.lang.reflect.Constructor;
import java.nio.file.*;
import java.util.List;
import java.io.*;
import static org.junit.Assert.*;

public class BookFileManagerTest {

    private static final String FILE = "Files/books.txt";

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
        Constructor<BookFileManager> c = BookFileManager.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuildFilePath(){
        assertEquals("Files/books.txt", BookFileManager.buildFilePath());
    }

    @Test
    public void testExists(){
        assertFalse(BookFileManager.exists());
        BookFileManager.saveBookToFile(new Book("T","A","1"));
        assertTrue(BookFileManager.exists());
    }

    @Test
    public void testFormat(){
        String r = BookFileManager.formatBook(new Book("X","Y","2"));
        assertEquals("X,Y,2", r);
    }

    @Test
    public void testParse(){
        Book b = BookFileManager.parseBook("M,N,3");
        assertEquals("M",b.getTitle());
        assertEquals("N",b.getAuthor());
        assertEquals("3",b.getISBN());
    }

    @Test
    public void testSave() throws Exception {
        BookFileManager.saveBookToFile(new Book("A","B","4"));
        String t = Files.readString(Paths.get(FILE));
        assertTrue(t.contains("A,B,4"));
    }

    @Test
    public void testLoadWhenNoFile(){
        List<Book> list = BookFileManager.loadBooksFromFile();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testLoadValid() throws Exception {
        Files.writeString(Paths.get(FILE),"C,D,5\n");
        List<Book> list = BookFileManager.loadBooksFromFile();
        assertEquals(1,list.size());
        assertEquals("C",list.get(0).getTitle());
    }
}
