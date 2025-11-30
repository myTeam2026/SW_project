package services;

import library.data.BookData;
import library.data.BookFileManager;
import library.data.LogFileManager;
import library.entities.Book;

public class add_book_service {

    public boolean addBook(String title, String author, String isbn) {
        if (BookData.getBookByISBN(isbn) != null) {
            LogFileManager.log("ADD_BOOK_FAILED: ISBN=" + isbn + " already exists");
            return false;
        }

        Book newBook = new Book(title, author, isbn);
        newBook.setAvailable(true);
        BookData.addBook(newBook);

        BookFileManager.saveBookToFile(newBook);
        LogFileManager.log("ADD_BOOK_SUCCESS: ISBN=" + isbn + ", TITLE=" + title);

        return true;
    }
}
