/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    package services;

import library.data.BookData;
import library.entities.Book;

public class add_book_service {

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


