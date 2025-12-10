package Tests;

import library.services.BookSearchService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

import library.data.BookData;
import library.entities.Book;



/**
 * Unit tests for the BookSearchService class.
 * 
 * This test class verifies that books can be correctly searched by title, author, or ISBN.
 * It also checks the behavior when searching for non-existing books.
 */
public class SearchBookTest {

    private BookSearchService searchService;

    /**
     * Initializes test data before each test.
     * Clears existing books and adds sample books for testing.
     */
    @Before
    public void setUp() {
        searchService = new BookSearchService();
        BookData.clearBooks();

        // Adding sample books
        BookData.addBook(new Book("Java Programming", "John Doe", "ISBN001"));
        BookData.addBook(new Book("Data Structures", "Jane Smith", "ISBN002"));
        BookData.addBook(new Book("Advanced Java", "John Doe", "ISBN003"));
    }

    /**
     * Cleans up after each test.
     * Clears all books from the data store.
     */
    @After
    public void tearDown() {
        BookData.clearBooks();
    }

    /**
     * Tests searching books by title.
     * Expects a single match with the correct ISBN.
     */
    @Test
    public void testSearchByTitle() {
        List<Book> results = searchService.searchByTitle("Java Programming");
        assertEquals(1, results.size());
        assertEquals("ISBN001", results.get(0).getISBN());
    }

    /**
     * Tests searching books by author.
     * Expects all books by "John Doe" to be returned.
     */
    @Test
    public void testSearchByAuthor() {
        List<Book> results = searchService.searchByAuthor("John Doe");
        assertEquals(2, results.size());
    }

    /**
     * Tests searching a book by ISBN.
     * Expects the book with that ISBN to be returned.
     */
    @Test
    public void testSearchByISBN() {
        Book book = searchService.searchByISBN("ISBN002");
        assertNotNull(book);
        assertEquals("Data Structures", book.getTitle());
    }

    /**
     * Tests searching for a non-existing book by ISBN and title.
     * Expects null or empty results.
     */
    @Test
    public void testSearchNonExistingBook() {
        Book book = searchService.searchByISBN("ISBN999");
        assertNull(book);

        List<Book> results = searchService.searchByTitle("Non Existing");
        assertTrue(results.isEmpty());
    }
}

