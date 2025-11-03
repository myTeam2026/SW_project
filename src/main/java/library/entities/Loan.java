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

    // Constructor لكتب
    public Loan(Book book, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = UUID.randomUUID().toString(); // توليد ID تلقائي
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.cd = null;
    }

    // Constructor لأقراص CD
    public Loan(CD cd, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = UUID.randomUUID().toString(); // توليد ID تلقائي
        this.cd = cd;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.book = null;
    }

    // -------------------- Getters --------------------
    public String getLoanId() { return loanId; }
    public User getUser() { return user; }
    public Book getBook() { return book; }
    public CD getCD() { return cd; } // بدون parameters
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    // -------------------- Setters --------------------
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
}

    // دالة مساعدة للتحقق من نوع الوسيلة المستعارة
    public boolean isBookLoan() { return book != null; }
    public boolean isCDLoan() { return cd != null; }
}
