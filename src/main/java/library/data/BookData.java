package library.data;

import library.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BookData {

    private static List<Book> books = new ArrayList<>();

    // الثابت لتجنب تكرار المؤلف
    private static final String TEST_AUTHOR = "Test Author";

    static {
        books.add(new Book("Design Patterns", "Erich Gamma", "222222"));
        books.add(new Book("Spring Framework", "Rod Johnson", "333333"));
        books.add(new Book("Database Systems", "Abraham Silberschatz", "444444"));
        books.add(new Book("Advanced Java", TEST_AUTHOR, "555555"));
        books.add(new Book("Software Engineering", TEST_AUTHOR, "666666"));
        books.add(new Book("Web Development", TEST_AUTHOR, "777777"));
        books.add(new Book("Test Book 999999", TEST_AUTHOR, "999999"));
    }

    private BookData() {} // منع إنشاء كائن

    public static List<Book> buildList() {
        return books;
    }

    public static int size() {
        return books.size();
    }

    public static boolean exists(String isbn) {
        return getBookByISBN(isbn) != null;
    }

    public static void addBook(Book book) {
        books.add(book);
    }

    public static Book getBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) return book;
        }
        return null;
    }

    public static List<Book> searchBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public static List<Book> searchBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public static void resetBooks() {
        for (Book book : books) book.setAvailable(true);
    }

    public static List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public static void clearBooks() {
        books.clear();
    }
}
