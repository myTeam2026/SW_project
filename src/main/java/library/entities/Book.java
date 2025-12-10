package library.entities;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private String author;
    private String isbn;
    private boolean available;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
        
    }

    private Book(){}

    public static Book build(String title,String author,String isbn){
        return new Book(title,author,isbn);
    }

    public static Book parse(String line){
        String[] p = line.split(",");
        Book b = new Book(p[0],p[1],p[2]);
        b.setAvailable(Boolean.parseBoolean(p[3]));
        return b;
    }

    public String format(){
        return title + "," + author + "," + isbn + "," + available;
    }

    public Book copy(){
        Book b = new Book(title,author,isbn);
        b.setAvailable(available);
        return b;
    }

    public void toggleAvailable(){
        available = !available;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return isbn; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setISBN(String isbn) { this.isbn = isbn; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() { return isbn.hashCode(); }

    @Override
    public String toString() {
        return "Book Title: " + title + ", Author Name: " + author + ", ISBN: " + isbn;
    }
}
