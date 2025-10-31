package library.entities;

import java.time.LocalDate;

public class Loan {
    private String loanId;
    private Book book;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    
    public Loan(Book book, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = "L" + System.currentTimeMillis();
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }
    
    // Getters
    public String getLoanId() { return loanId; }
    public Book getBook() { return book; }
    public User getUser() { return user; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    
    // Setters
    public void setReturnDate(LocalDate returnDate) { 
        this.returnDate = returnDate; 
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}