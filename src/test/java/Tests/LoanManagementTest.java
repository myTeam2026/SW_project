package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;
import java.util.List;

public class LoanManagementTest {

    private User testUser;
    private Book testBook;

    @Before
    public void setUp() {
        LoanData.clearLoans();
        BookData.clearBooks();
        UserData.clearUsers();

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
    }

    @Test
    public void testDetectOverdueBook() {
        Loan overdueLoan = new Loan(testBook, testUser,
                LocalDate.now().minusDays(35),
                LocalDate.now().minusDays(7));

        LoanData.addLoan(overdueLoan);

        List<Loan> userLoans = LoanData.getLoansByUser(testUser.getId());
        Loan loan = userLoans.get(0);

        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        assertTrue(isOverdue);
    }

    @Test
    public void testNotOverdueBook() {
        Loan notOverdueLoan = new Loan(testBook, testUser,
                LocalDate.now(),
                LocalDate.now().plusDays(7));

        LoanData.addLoan(notOverdueLoan);

        Loan loan = LoanData.getLoansByUser(testUser.getId()).get(0);
        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());

        assertFalse(isOverdue);
    }

    @Test
    public void testGetUserActiveLoans() {
        Loan loan1 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan1);

        Book book2 = new Book("Second Book", "Author2", "ISBN002");
        book2.setAvailable(true);
        BookData.addBook(book2);

        Loan loan2 = new Loan(book2, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan2);

        List<Loan> activeLoans = LoanData.getLoansByUser(testUser.getId());

        assertEquals(2, activeLoans.size());
    }

    @Test
    public void testLoanExactly28Days() {
        Loan loan = new Loan(testBook, testUser,
                LocalDate.now().minusDays(28),
                LocalDate.now());

        LoanData.addLoan(loan);

        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        assertFalse(isOverdue);
    }

    @Test
    public void testLoan29DaysOverdue() {
        Loan loan = new Loan(testBook, testUser,
                LocalDate.now().minusDays(57),
                LocalDate.now().minusDays(29));

        LoanData.addLoan(loan);

        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        assertTrue(isOverdue);
    }

    @Test
    public void testExtendLoanDueDate() {
        LocalDate original = LocalDate.now().plusDays(14);
        Loan loan = new Loan(testBook, testUser, LocalDate.now(), original);

        LoanData.addLoan(loan);

        LocalDate newDue = original.plusDays(7);
        loan.setDueDate(newDue);

        assertEquals(newDue, loan.getDueDate());
    }

    @Test
    public void testGetActiveLoans() {
        Loan loan1 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        Loan loan2 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(14));

        LoanData.addLoan(loan1);
        LoanData.addLoan(loan2);

        List<Loan> activeLoans = LoanData.getActiveLoans();

        assertEquals(2, activeLoans.size());
    }

    @Test
    public void testReturnBook() {
        Loan loan = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan);

        loan.setReturnDate(LocalDate.now());

        assertNotNull(loan.getReturnDate());
        List<Loan> activeLoans = LoanData.getLoansByUser(testUser.getId());
        assertTrue(activeLoans.isEmpty());
    }

    @Test
    public void testNoActiveLoansForUser() {
        List<Loan> active = LoanData.getLoansByUser("NO_USER");
        assertTrue(active.isEmpty());
    }

    @Test
    public void testLoanCreation() {
        LocalDate borrow = LocalDate.now();
        LocalDate due = borrow.plusDays(28);

        Loan loan = new Loan(testBook, testUser, borrow, due);
        LoanData.addLoan(loan);

        assertEquals(testBook, loan.getBook());
        assertEquals(testUser, loan.getUser());
        assertEquals(borrow, loan.getBorrowDate());
        assertEquals(due, loan.getDueDate());
        assertNull(loan.getReturnDate());
    }
}
