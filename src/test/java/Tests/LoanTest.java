package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import library.entities.Book;
import library.entities.CD;
import library.entities.User;
import library.entities.Loan;

/**
 * Unit tests for the {@link Loan} class.
 * <p>
 * These tests verify the correct behavior of loans for books and CDs,
 * including constructors, getters, setters, and loan type identification.
 * </p>
 */
public class LoanTest {

    /** A test user associated with the loans. */
    private User user;

    /** A test book for book loans. */
    private Book book;

    /** A test CD for CD loans. */
    private CD cd;

    /** A loan instance for the book. */
    private Loan bookLoan;

    /** A loan instance for the CD. */
    private Loan cdLoan;

    /** The borrow date for the loans. */
    private LocalDate borrowDate;

    /** The due date for the loans. */
    private LocalDate dueDate;

    /**
     * Sets up the test environment before each test.
     * <p>
     * Initializes a test user, a book, a CD, and loans for both book and CD.
     * </p>
     */
    @Before
    public void setUp() {
        user = new User("U001", "John Doe", "john@email.com");
        book = new Book("Book Title", "Author", "ISBN001");
        cd = new CD("CD Title", "Artist", "CD001");
        borrowDate = LocalDate.now().minusDays(5);
        dueDate = LocalDate.now().plusDays(10);
        bookLoan = new Loan(book, user, borrowDate, dueDate);
        cdLoan = new Loan(cd, user, borrowDate, dueDate);
    }

    /**
     * Tests the constructor and getters for a book loan.
     */
    @Test
    public void testBookLoanConstructorAndGetters() {
        assertNotNull(bookLoan.getLoanId());
        assertEquals(book, bookLoan.getBook());
        assertEquals(user, bookLoan.getUser());
        assertEquals(borrowDate, bookLoan.getBorrowDate());
        assertEquals(dueDate, bookLoan.getDueDate());
        assertNull(bookLoan.getReturnDate());
        assertTrue(bookLoan.isBookLoan());
        assertFalse(bookLoan.isCDLoan());
    }

    /**
     * Tests the constructor and getters for a CD loan.
     */
    @Test
    public void testCDLoanConstructorAndGetters() {
        assertNotNull(cdLoan.getLoanId());
        assertEquals(cd, cdLoan.getCD());
        assertEquals(user, cdLoan.getUser());
        assertEquals(borrowDate, cdLoan.getBorrowDate());
        assertEquals(dueDate, cdLoan.getDueDate());
        assertNull(cdLoan.getReturnDate());
        assertTrue(cdLoan.isCDLoan());
        assertFalse(cdLoan.isBookLoan());
    }

    /**
     * Tests setting and getting the return date of a loan.
     */
    @Test
    public void testSetReturnDateAndGetReturnDate() {
        LocalDate returnDate = LocalDate.now();
        bookLoan.setReturnDate(returnDate);
        assertEquals(returnDate, bookLoan.getReturnDate());
    }

    /**
     * Tests setting and getting the due date of a loan.
     */
    @Test
    public void testSetDueDateAndGetDueDate() {
        LocalDate newDue = LocalDate.now().plusDays(15);
        cdLoan.setDueDate(newDue);
        assertEquals(newDue, cdLoan.getDueDate());
    }

    /**
     * Tests creating a loan with null fields.
     * <p>
     * Verifies that a loan can handle null book and CD references correctly.
     * </p>
     */
    @Test
    public void testNullFieldsLoan() {
        Loan emptyLoan = new Loan((Book) null, user, borrowDate, dueDate);
        assertNull(emptyLoan.getBook());
        assertFalse(emptyLoan.isCDLoan());
        assertTrue(emptyLoan.isBookLoan() || !emptyLoan.isCDLoan());
    }

    /**
     * Tests the flow of setting and retrieving the return date.
     */
    @Test
    public void testReturnDateFlow() {
        assertNull(bookLoan.getReturnDate());
        LocalDate now = LocalDate.now();
        bookLoan.setReturnDate(now);
        assertEquals(now, bookLoan.getReturnDate());
    }
}
