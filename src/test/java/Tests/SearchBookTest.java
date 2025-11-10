/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tests;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

import services.BookSearchService;
import library.data.BookData;
import library.entities.Book;

public class SearchBookTest {

    private BookSearchService searchService;

    @Before
    public void setUp() {
        searchService = new BookSearchService();
        BookData.clearBooks();

        // إضافة بعض الكتب للاختبار
        BookData.addBook(new Book("Java Programming", "John Doe", "ISBN001"));
        BookData.addBook(new Book("Data Structures", "Jane Smith", "ISBN002"));
        BookData.addBook(new Book("Advanced Java", "John Doe", "ISBN003"));
    }

    @After
    public void tearDown() {
        BookData.clearBooks();
    }

    @Test
    public void testSearchByTitle() {
        List<Book> results = searchService.searchByTitle("Java Programming");
        assertEquals(1, results.size());
        assertEquals("ISBN001", results.get(0).getISBN());
    }

    @Test
    public void testSearchByAuthor() {
        List<Book> results = searchService.searchByAuthor("John Doe");
        assertEquals(2, results.size());
    }

    @Test
    public void testSearchByISBN() {
        Book book = searchService.searchByISBN("ISBN002");
        assertNotNull(book);
        assertEquals("Data Structures", book.getTitle());
    }

    @Test
    public void testSearchNonExistingBook() {
        Book book = searchService.searchByISBN("ISBN999");
        assertNull(book);

        List<Book> results = searchService.searchByTitle("Non Existing");
        assertTrue(results.isEmpty());
    }
}
