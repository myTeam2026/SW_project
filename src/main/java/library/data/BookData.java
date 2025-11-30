package library.data;

import library.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookData {

    private static List<Book> books = new ArrayList<>();

    static {
      books.add(new Book("aa", "a", "1"));
      books.add(new Book("ss", "s", "2"));
      books.add(new Book("dd", "d", "3"));
      books.add(new Book("ff", "f", "4"));
    }

    
    public static void addBook(Book book) {
        books.add(book);
    }

    public static Book getBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) {
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
    public static List<Book> getAllBooks() {
    return new ArrayList<>(books); // تعيد نسخة من جميع الكتب
}

    public static void clearBooks() {
        books.clear();
    }
}