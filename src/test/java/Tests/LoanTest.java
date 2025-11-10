package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import library.entities.Book;
import library.entities.CD;
import library.entities.User;
import library.entities.Loan;

public class LoanTest {

    private User user;
    private Book book;
    private CD cd;
    private Loan bookLoan;
    private Loan cdLoan;
    private LocalDate borrowDate;
    private LocalDate dueDate;

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

    @Test
    public void testSetReturnDateAndGetReturnDate() {
        LocalDate returnDate = LocalDate.now();
        bookLoan.setReturnDate(returnDate);
        assertEquals(returnDate, bookLoan.getReturnDate());
    }

    @Test
    public void testSetDueDateAndGetDueDate() {
        LocalDate newDue = LocalDate.now().plusDays(15);
        cdLoan.setDueDate(newDue);
        assertEquals(newDue, cdLoan.getDueDate());
    }

    @Test
    public void testNullFieldsLoan() {
        Loan emptyLoan = new Loan((Book) null, user, borrowDate, dueDate);
        assertNull(emptyLoan.getBook());
        assertFalse(emptyLoan.isCDLoan());
        assertTrue(emptyLoan.isBookLoan() || !emptyLoan.isCDLoan());
    }

    @Test
    public void testReturnDateFlow() {
        assertNull(bookLoan.getReturnDate());
        LocalDate now = LocalDate.now();
        bookLoan.setReturnDate(now);
        assertEquals(now, bookLoan.getReturnDate());
    }
}
