package library.data;

import library.entities.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides file-based data management for storing and loading books.
 * <p>
 * This class handles saving new books to a text file and loading all stored
 * books from that file. Each book is stored in CSV format:
 * <br><code>title,author,isbn</code>
 * </p>
 *
 * @author Hamsa
 * @version 1.1
 */
public class BookFileManager {

    /** Path to the text file where books are stored. */
    private static final String FILE_PATH = "Files/books.txt";

    /**
     * Saves a new book to the books file.
     * <p>
     * If the file or its parent directory does not exist, they will be created
     * automatically. The book is appended in CSV format.
     * </p>
     *
     * @param book the Book object to save
     */
    public static void saveBookToFile(Book book) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(book.getTitle() + "," + book.getAuthor() + "," + book.getISBN());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving book: " + e.getMessage());
        }
    }

    /**
     * Loads all books stored inside the file.
     * <p>
     * If the file does not exist, an empty list is returned. Each line is parsed
     * into a {@link Book} object if it contains three comma-separated fields.
     * </p>
     *
     * @return a list of all books found in the file
     */
    public static List<Book> loadBooksFromFile() {
        List<Book> books = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return books;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3) {
                    books.add(new Book(p[0], p[1], p[2]));
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error reading books: " + e.getMessage());
        }

        return books;
    }
}
