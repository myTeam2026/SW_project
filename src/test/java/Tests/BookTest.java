package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.Book;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        book = new Book("Book Title", "Author Name", "ISBN001");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Book Title", book.getTitle());
        assertEquals("Author Name", book.getAuthor());
        assertEquals("ISBN001", book.getISBN());
        assertTrue(book.isAvailable());
    }

    @Test
    public void testSetTitle() {
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
    }

    @Test
    public void testSetAuthor() {
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    public void testSetISBN() {
        book.setISBN("NEW123");
        assertEquals("NEW123", book.getISBN());
    }

    @Test
    public void testAvailabilityTrue() {
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    @Test
    public void testAvailabilityFalse() {
        book.setAvailable(false);
        assertFalse(book.isAvailable());
    }

    @Test
    public void testToString() {
        String text = book.toString();
        assertTrue(text.contains("Book Title"));
        assertTrue(text.contains("Author Name"));
        assertTrue(text.contains("ISBN001"));
    }
}
