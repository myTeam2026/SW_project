package library.data;

import library.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides static data for books in the library system.
 * <p>
 * This class simulates an in-memory database of books and provides
 * methods to add, search, reset, and retrieve book data.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class BookData {

    /**
     * List that stores all Book objects.
     * Pre-populated with sample books in the static block.
     */
    private static List<Book> books = new ArrayList<>();

    /**
     * Static block to initialize the books list with sample data.
     */
    static {
        books.add(new Book("Java Programming", "John Doe", "123456"));
        books.add(new Book("Data Structures", "Jane Smith", "789012"));
        books.add(new Book("Algorithms", "Robert Martin", "345678"));
        books.add(new Book("Clean Code", "Robert Martin", "111111"));
        books.add(new Book("Design Patterns", "Erich Gamma", "222222"));
        books.add(new Book("Spring Framework", "Rod Johnson", "333333"));
        books.add(new Book("Database Systems", "Abraham Silberschatz", "444444"));
        books.add(new Book("Advanced Java", "Test Author", "555555"));
        books.add(new Book("Software Engineering", "Test Author", "666666"));
        books.add(new Book("Web Development", "Test Author", "777777"));
        books.add(new Book("Test Book 999999", "Test Author", "999999"));
    }

    /**
     * Adds a new book to the list.
     *
     * @param book the Book object to add
     */
    public static void addBook(Book book) {
        books.add(book);
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn the ISBN of the book to search for
     * @return the Book object if found; otherwise null
     */
    public static Book getBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Searches for books by title (case-insensitive).
     *
     * @param title the title to search for
     * @return a list of books matching the given title
     */
    public static List<Book> searchBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }
    
    /**
     * Searches for books by author (case-insensitive).
     *
     * @param author the author to search for
     * @return a list of books written by the given author
     */
    public static List<Book> searchBooksByAuthor(String author) {
        return books.stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                    .collect(Collectors.toList());
    }

    /**
     * Resets all books to be available.
     * Typically used at the start of a new session or test.
     */
    public static void resetBooks() {
        for (Book book : books) {
            book.setAvailable(true);
        }
    }

    /**
     * Returns a copy of all books in the system.
     *
     * @return a list of all Book objects
     */
    public static List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Clears all books from the list.
     * Typically used for testing or resetting the system.
     */
    public static void clearBooks() {
        books.clear();
    }
}
