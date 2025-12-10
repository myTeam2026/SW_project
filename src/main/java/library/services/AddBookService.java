package library.services;

import library.data.BookData;
import library.entities.Book;

/**
 * Service class responsible for adding new books to the library system.
 * <p>
 * Ensures that a book with the same ISBN does not already exist before adding.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class AddBookService {

    /**
     * Adds a new book to the library.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @param isbn   the ISBN of the book (must be unique)
     * @return true if the book was successfully added; false if a book with the same ISBN already exists
     */
    public boolean addBook(String title, String author, String isbn) {
        // التحقق من وجود الكتاب مسبقاً
        if (BookData.getBookByISBN(isbn) != null) {
            return false; // الكتاب موجود بالفعل
        }

        Book newBook = new Book(title, author, isbn);
        newBook.setAvailable(true); // الكتاب متاح للاستعارة
        BookData.addBook(newBook);
        return true;
    }
}
