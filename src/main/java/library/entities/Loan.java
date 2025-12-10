package library.entities;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a loan of either a Book or a CD to a library user.
 * <p>
 * This class stores loan details such as loan ID, the borrowed item (Book or CD),
 * the borrowing user, borrow date, due date, and return date.
 * </p>
 * <p>
 * Each loan can either be for a Book or a CD, not both.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class Loan {

    /**
     * The unique ID of the loan.
     */
    private String loanId;

    /**
     * The user who borrowed the item.
     */
    private User user;

    /**
     * The borrowed book, if applicable.
     */
    private Book book;

    /**
     * The borrowed CD, if applicable.
     */
    private CD cd;

    /**
     * The date the item was borrowed.
     */
    private LocalDate borrowDate;

    /**
     * The due date for returning the item.
     */
    private LocalDate dueDate;

    /**
     * The date the item was actually returned, null if not returned yet.
     */
    private LocalDate returnDate;

    /**
     * Constructs a Loan for a Book.
     *
     * @param book       the book being borrowed
     * @param user       the user borrowing the book
     * @param borrowDate the date of borrowing
     * @param dueDate    the due date for returning the book
     */
    public Loan(Book book, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = UUID.randomUUID().toString();
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.cd = null;
    }

    /**
     * Constructs a Loan for a CD.
     *
     * @param cd         the CD being borrowed
     * @param user       the user borrowing the CD
     * @param borrowDate the date of borrowing
     * @param dueDate    the due date for returning the CD
     */
    public Loan(CD cd, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = UUID.randomUUID().toString();
        this.cd = cd;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.book = null;
    }

   public Loan(String loanId, User user, LocalDate borrowDate, LocalDate dueDate, CD cd) {
    this.loanId = loanId;
    this.user = user;
    this.borrowDate = borrowDate;
    this.dueDate = dueDate;
    this.returnDate = null;
    this.cd = cd;
    this.book = null;
}


    // -------------------- Getters --------------------

    /**
     * Returns the loan ID.
     *
     * @return the loan ID
     */
    public String getLoanId() {
        return loanId;
    }

    /**
     * Returns the user who borrowed the item.
     *
     * @return the borrowing user
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the borrowed book, or null if this is a CD loan.
     *
     * @return the borrowed book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Returns the borrowed CD, or null if this is a book loan.
     *
     * @return the borrowed CD
     */
    public CD getCD() {
        return cd;
    }

    /**
     * Returns the borrow date.
     *
     * @return the borrow date
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Returns the due date.
     *
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Returns the actual return date, or null if not yet returned.
     *
     * @return the return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    // -------------------- Setters --------------------

    /**
     * Sets the return date for the loan.
     *
     * @param returnDate the return date to set
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Updates the due date for the loan.
     *
     * @param dueDate the new due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // -------------------- Utility Methods --------------------

    /**
     * Checks whether this loan is for a book.
     *
     * @return true if the loan is for a book, false otherwise
     */
    public boolean isBookLoan() {
        return book != null;
    }

    /**
     * Checks whether this loan is for a CD.
     *
     * @return true if the loan is for a CD, false otherwise
     */
    public boolean isCDLoan() {
        return cd != null;
    }
    public boolean isReturned() {
    return returnDate != null;
}

}

