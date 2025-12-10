package Tests;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import library.data.BookData;
import library.entities.Book;
import library.services.AddBookService;

/**
 * Unit test class for {@link AddBookService}.
 * <p>
 * Tests adding books to the library, including success cases and duplicate book prevention.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class AddBookTest {

    /** The service used to add books */
    private AddBookService bookService;

    /**
     * Sets up the test environment before each test.
     * <p>
 Initializes the AddBookService and clears any existing books.
 </p>
     */
    @Before
    public void setUp() {
        bookService = new AddBookService();
        BookData.clearBooks();
    }

    /**
     * Cleans up after each test.
     * <p>
     * Clears the books data to ensure a clean state for the next test.
     * </p>
     */
    @After
    public void tearDown() {
        BookData.clearBooks();
    }

    /**
     * Tests that adding a new book succeeds.
     * <p>
     * Verifies that the book is retrievable by ISBN and its properties are correct.
     * </p>
     */
    @Test
    public void testAddBookSuccess() {
        boolean result = bookService.addBook("Java Programming", "John Doe", "ISBN100");
        assertTrue("Book should be added successfully", result);

        Book book = BookData.getBookByISBN("ISBN100");
        assertNotNull("Book should be retrievable by ISBN", book);
        assertEquals("Java Programming", book.getTitle());
        assertEquals("John Doe", book.getAuthor());
        assertTrue("Book should be available", book.isAvailable());
    }

    /**
     * Tests that adding a duplicate book fails.
     * <p>
     * Verifies that trying to add a book with an existing ISBN returns false.
     * </p>
     */
    @Test
    public void testAddDuplicateBookFails() {
        bookService.addBook("Java Programming", "John Doe", "ISBN100");
        boolean result = bookService.addBook("Java Programming", "John Doe", "ISBN100");
        assertFalse("Adding duplicate book should fail", result);
    }
}
