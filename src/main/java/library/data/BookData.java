package library.data;

import library.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookData {

    private static List<Book> books = new ArrayList<>();

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

    public static void addBook(Book book) {
        books.add(book);
    }

    public static Book getBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
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
        for (Book book : books) {
            book.setAvailable(true);
        }
    }
    
    public static void clearBooks() {
        books.clear();
    }
}