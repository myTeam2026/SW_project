package library.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {

    private String loanId;
    private User user;
    private Book book;
    private CD cd;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = UUID.randomUUID().toString();
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.cd = null;
    }

    public Loan(CD cd, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = UUID.randomUUID().toString();
        this.cd = cd;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.book = null;
    }

    public String getLoanId() { return loanId; }
    public User getUser() { return user; }
    public Book getBook() { return book; }
    public CD getCD() { return cd; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isBookLoan() { return book != null; }
    public boolean isCDLoan() { return cd != null; }

    // ⭐ إضافة مهمة
    public boolean isReturned() {
        return returnDate != null;
    }
}
