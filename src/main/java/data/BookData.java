/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    package library.data;

import library.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookData {

    private static List<Book> books = new ArrayList<>();

    // إضافة كتاب جديد
    public static void addBook(Book book) {
        books.add(book);
    }

    // البحث عن كتاب باستخدام ISBN
    public static Book getBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    // البحث عن كتب باستخدام العنوان
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


    // مسح جميع الكتب (اختياري لإعادة الاختبارات)
    public static void clearBooks() {
        books.clear();
    }
}


