package library.entities;

/**
 * Represents a book in the library system.
 * <p>
 * This class stores the book's title, author, ISBN, and availability status.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class Book {

    /** The title of the book. */
    private String title;

    /** The author of the book. */
    private String author;

    /** The unique ISBN identifier for the book. */
    private String isbn;

    /** Indicates whether the book is currently available. */
    private boolean available;

    /**
     * Constructs a new Book with the specified title, author, and ISBN.
     * The book is initially available.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @param isbn   the ISBN of the book
     */
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    /** @return the book title */
    public String getTitle() { return title; }

    /** @return the book author */
    public String getAuthor() { return author; }

    /** @return the book ISBN */
    public String getISBN() { return isbn; }

    /** @return true if available, false otherwise */
    public boolean isAvailable() { return available; }

    /** Sets the availability status of the book. */
    public void setAvailable(boolean available) { this.available = available; }

    /** Compares this book to another object for equality based on ISBN. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return isbn.equals(other.isbn);
    }

    /** Returns the hash code for this book based on its ISBN. */
    @Override
    public int hashCode() { return isbn.hashCode(); }

    /** Sets the title of the book. */
    public void setTitle(String title) { this.title = title; }

    /** Sets the author of the book. */
    public void setAuthor(String author) { this.author = author; }

    /** Sets the ISBN of the book. */
    public void setISBN(String isbn) { this.isbn = isbn; }

    /** @return a string representation of the book */
    @Override
    public String toString() {
        return "Book Title: " + title +
               ", Author Name: " + author +
               ", ISBN: " + isbn;
    }
}
