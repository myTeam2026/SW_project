package Tests;

import library.data.BookData;
import library.entities.Book;
import org.junit.*;
import java.lang.reflect.Constructor;
import java.util.List;
import static org.junit.Assert.*;

public class BookDataTest {

    @Before
    public void setup(){
        BookData.clearBooks();
        BookData.addBook(new Book("X","A","1"));
        BookData.addBook(new Book("Y","B","2"));
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<BookData> c = BookData.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuildList(){
        assertNotNull(BookData.buildList());
    }

    @Test
    public void testSize(){
        assertEquals(2, BookData.size());
    }

    @Test
    public void testExists(){
        assertTrue(BookData.exists("1"));
    }

    @Test
    public void testNotExists(){
        assertFalse(BookData.exists("999"));
    }

    @Test
    public void testGet(){
        Book b = BookData.getBookByISBN("1");
        assertEquals("X",b.getTitle());
    }

    @Test
    public void testAdd(){
        BookData.addBook(new Book("Z","C","3"));
        assertTrue(BookData.exists("3"));
    }

    @Test
    public void testSearchTitle(){
        List<Book> list = BookData.searchBooksByTitle("X");
        assertEquals(1,list.size());
    }

    @Test
    public void testSearchAuthor(){
        List<Book> list = BookData.searchBooksByAuthor("A");
        assertEquals(1,list.size());
    }

    @Test
    public void testReset(){
        BookData.resetBooks();
        Book b = BookData.getBookByISBN("1");
        assertTrue(b.isAvailable());
    }

    @Test
    public void testGetAll(){
        assertEquals(2, BookData.getAllBooks().size());
    }

    @Test
    public void testClear(){
        BookData.clearBooks();
        assertEquals(0, BookData.size());
    }
}
