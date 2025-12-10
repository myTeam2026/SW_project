package library.data;

import library.entities.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class BookFileManager {

    private static final String FILE_PATH = "Files/books.txt";

    private BookFileManager(){}

    public static String buildFilePath(){
        return FILE_PATH;
    }

    public static boolean exists(){
        return new File(FILE_PATH).exists();
    }

    public static String formatBook(Book b){
        return b.getTitle() + "," + b.getAuthor() + "," + b.getISBN();
    }

    public static Book parseBook(String line){
        String[] p = line.split(",");
        return new Book(p[0],p[1],p[2]);
    }

    public static void saveBookToFile(Book book) {
        try {
            File f = new File(FILE_PATH);
            f.getParentFile().mkdirs();
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write(formatBook(book));
            bw.newLine();
            bw.close();
        } catch (Exception e) {}
    }

    public static List<Book> loadBooksFromFile() {
        List<Book> books = new ArrayList<>();
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) return books;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3) books.add(parseBook(line));
            }
            br.close();
        } catch (Exception e) {}
        return books;
    }
}
