package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import services.BorrowService;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import library.data.FineData;

public class BorrowBookTest {

    private BorrowService borrowService;
    private User testUser;
    private Book testBook;

    @Before
    public void setUp() {
        borrowService = new BorrowService();
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        FineData.clearFines();

        testUser = new User("USER001", "John Doe", "john@email.com");
        UserData.addUser(testUser);

        testBook = new Book("Test Book", "Test Author", "ISBN001");
        testBook.setAvailable(true);
        BookData.addBook(testBook);
    }

    @After
    public void tearDown() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();
        FineData.clearFines();
    }

    @Test
    public void testBorrowBookSuccess() {
        String result = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Success: Book borrowed successfully", result);

        Book borrowedBook = BookData.getBookByISBN("ISBN001");
        assertFalse(borrowedBook.isAvailable());

        List<Loan> loans = LoanData.getLoansByUser("USER001");
        assertEquals(1, loans.size());
        Loan loan = loans.get(0);
        assertEquals(LocalDate.now().plusDays(28), loan.getDueDate());
    }

    @Test
    public void testBorrowBookNotFound() {
        String result = borrowService.borrowBook("WRONG", "USER001");
        assertEquals("Error: Book not found", result);
    }

    @Test
    public void testBorrowBookUnavailable() {
        testBook.setAvailable(false);
        String result = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Error: Book is not available", result);
    }

    @Test
    public void testBorrowWithUnpaidFines() {
        testUser.addFine(50.0);
        String result = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Error: Cannot borrow - user has unpaid fines", result);
    }

    @Test
    public void testCanUserBorrowAllowed() {
        boolean canBorrow = borrowService.canUserBorrow("USER001");
        assertTrue(canBorrow);
    }

    @Test
    public void testCanUserBorrow_BlockedDueToOverdue() {
        String resultBorrow = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Success: Book borrowed successfully", resultBorrow);

        List<Loan> loans = LoanData.getLoansByUser("USER001");
        assertFalse(loans.isEmpty());

        Loan loan = loans.get(0);
        loan.setDueDate(LocalDate.now().minusDays(10));
        loan.setReturnDate(null);

        boolean result = borrowService.canUserBorrow("USER001");
        assertFalse(result);
    }

    @Test
    public void testReturnBook() {
        borrowService.borrowBook("ISBN001", "USER001");
        List<Loan> loans = LoanData.getLoansByUser("USER001");
        assertEquals(1, loans.size());
        Loan loan = loans.get(0);

        boolean returnResult = borrowService.returnBook(loan.getLoanId());
        assertTrue(returnResult);
        assertTrue(testBook.isAvailable());
        assertNotNull(loan.getReturnDate());
    }

    @Test
    public void testGetUserLoans() {
        String resultBorrow = borrowService.borrowBook("ISBN001", "USER001");
        assertEquals("Success: Book borrowed successfully", resultBorrow);

        List<Loan> loans = borrowService.getUserLoans("USER001");
        assertEquals(1, loans.size());
        assertEquals("ISBN001", loans.get(0).getBook().getISBN());
    }

    @Test
    public void testBorrowLimitReached() {
        Book b2 = new Book("B2", "A2", "ISBN002");
        Book b3 = new Book("B3", "A3", "ISBN003");
        Book b4 = new Book("B4", "A4", "ISBN004");
        b2.setAvailable(true);
        b3.setAvailable(true);
        b4.setAvailable(true);
        BookData.addBook(b2);
        BookData.addBook(b3);
        BookData.addBook(b4);

        assertEquals("Success: Book borrowed successfully", borrowService.borrowBook("ISBN001", "USER001"));
        assertEquals("Success: Book borrowed successfully", borrowService.borrowBook("ISBN002", "USER001"));
        assertEquals("Success: Book borrowed successfully", borrowService.borrowBook("ISBN003", "USER001"));

        String result = borrowService.borrowBook("ISBN004", "USER001");
        assertEquals("Error: Cannot borrow - maximum limit reached", result);

        List<Loan> loans = LoanData.getLoansByUser("USER001");
        assertEquals(3, loans.size());
    }
}
