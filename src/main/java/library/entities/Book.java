package library.entities;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean available;  // ⬅️ نضيف هذا الحقل

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;  // ⬅️ كل كتاب جديد بيكون متاح
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return available; }  // ⬅️ نضيف هذا الـ getter

    // Setter for available
    public void setAvailable(boolean available) {  // ⬅️ نضيف هذا الـ setter
        this.available = available;
    }

    // Equals & hashCode based on ISBN
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}