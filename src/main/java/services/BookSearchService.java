/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    package services;

import library.data.BookData;
import library.entities.Book;
import java.util.List;

public class BookSearchService {

    public List<Book> searchByTitle(String title) {
        return BookData.searchBooksByTitle(title);
    }

    public List<Book> searchByAuthor(String author) {
        return BookData.searchBooksByAuthor(author);
    }

    public Book searchByISBN(String isbn) {
        return BookData.getBookByISBN(isbn);
    }
}



