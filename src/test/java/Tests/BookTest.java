package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import library.entities.Book;

/**
 * Unit tests for the {@link Book} entity.
 * <p>
 * This class verifies the correctness of the constructor,
 * getter/setter methods, availability handling, and the
 * {@code toString()} representation of a book.
 * </p>
 *
 * @version 1.1
 * author Hamsa
 */
public class BookTest {

    /** Sample book instance used across tests. */
    private Book book;

    /**
     * Initializes a sample book before each test.
     */
    @Before
    public void setUp() {
        book = new Book("Book Title", "Author Name", "ISBN001");
    }

    /**
     * Tests the constructor and getter methods of the Book class.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("Book Title", book.getTitle());
        assertEquals("Author Name", book.getAuthor());
        assertEquals("ISBN001", book.getISBN());
        assertTrue(book.isAvailable());
    }

    /**
     * Tests updating the book title.
     */
    @Test
    public void testSetTitle() {
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
    }

    /**
     * Tests updating the book author.
     */
    @Test
    public void testSetAuthor() {
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
    }

    /**
     * Tests updating the ISBN value.
     */
    @Test
    public void testSetISBN() {
        book.setISBN("NEW123");
        assertEquals("NEW123", book.getISBN());
    }

    /**
     * Tests setting the availability flag to true.
     */
    @Test
    public void testAvailabilityTrue() {
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    /**
     * Tests setting the availability flag to false.
     */
    @Test
    public void testAvailabilityFalse() {
        book.setAvailable(false);
        assertFalse(book.isAvailable());
    }

    /**
     * Tests the {@code toString()} method for expected content.
     */
    @Test
    public void testToString() {
        String text = book.toString();
        assertTrue(text.contains("Book Title"));
        assertTrue(text.contains("Author Name"));
        assertTrue(text.contains("ISBN001"));
    }
}
