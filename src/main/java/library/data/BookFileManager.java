package library.data;

import library.entities.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookFileManager {

    private static final String FILE_PATH = "Files/books.txt";

    // حفظ كتاب جديد في ملف الكتب
    public static void saveBookToFile(Book book) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(book.getTitle() + "," + book.getAuthor() + "," + book.getISBN());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving book: " + e.getMessage());
        }
    }

    // قراءة جميع الكتب من الملف
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
