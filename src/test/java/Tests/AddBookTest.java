
    package Tests;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


import library.data.BookData;
import library.entities.Book;
import services.add_book_service;

public class AddBookTest {

    private add_book_service bookService;

    @Before
    public void setUp() {
        bookService = new add_book_service();
        BookData.clearBooks();
    }

    @After
    public void tearDown() {
        BookData.clearBooks();
    }

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

    @Test
    public void testAddDuplicateBookFails() {
        bookService.addBook("Java Programming", "John Doe", "ISBN100");
        boolean result = bookService.addBook("Java Programming", "John Doe", "ISBN100");
        assertFalse("Adding duplicate book should fail", result);
    }
}


