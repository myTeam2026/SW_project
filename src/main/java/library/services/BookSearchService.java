package library.services;
import library.data.BookData;
import library.entities.Book;
import java.util.List;


/**
 * Provides services to search for books in the library.
 * <p>
 * This class allows searching books by title, author, or ISBN
 * using the data provided by {@link BookData}.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class BookSearchService {

    /**
     * Searches for books with a matching title.
     *
     * @param title the title to search for
     * @return a list of books with the specified title
     */
    public List<Book> searchByTitle(String title) {
        return BookData.searchBooksByTitle(title);
    }

    /**
     * Searches for books by a specific author.
     *
     * @param author the author's name to search for
     * @return a list of books written by the specified author
     */
    public List<Book> searchByAuthor(String author) {
        return BookData.searchBooksByAuthor(author);
    }

    /**
     * Searches for a book with a specific ISBN.
     *
     * @param isbn the ISBN of the book to search for
     * @return the book with the specified ISBN, or null if not found
     */
    public Book searchByISBN(String isbn) {
        return BookData.getBookByISBN(isbn);
    }
}

