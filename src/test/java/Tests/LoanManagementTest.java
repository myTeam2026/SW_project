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
        testBook.setAvailable(false); 
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
        
        assertTrue("Book should be overdue", isOverdue);
        assertTrue("Due date should be in past", loan.getDueDate().isBefore(LocalDate.now()));
    }
    
    @Test
    public void testNotOverdueBook() {
        Loan notOverdueLoan = new Loan(testBook, testUser, 
                                     LocalDate.now(), 
                                     LocalDate.now().plusDays(7));
        LoanData.addLoan(notOverdueLoan);
        
        List<Loan> userLoans = LoanData.getLoansByUser(testUser.getId());
        Loan loan = userLoans.get(0);
        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        
        assertFalse("Book should not be overdue", isOverdue);
        assertTrue("Due date should be in future", loan.getDueDate().isAfter(LocalDate.now()));
    }
    
    @Test
    public void testGetUserActiveLoans() {

    	Loan loan1 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan1);
        
        Book book2 = new Book("Second Book", "Author2", "ISBN002");
        book2.setAvailable(false);
        BookData.addBook(book2);
        Loan loan2 = new Loan(book2, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan2);
        

        List<Loan> activeLoans = LoanData.getLoansByUser(testUser.getId());
        

        assertFalse("Should have active loans", activeLoans.isEmpty());
        assertEquals("Should have 2 active loans", 2, activeLoans.size());
    }
    
    @Test
    public void testLoanExactly28Days() {

    	Loan loan = new Loan(testBook, testUser, 
                           LocalDate.now().minusDays(28), 
                           LocalDate.now());
        LoanData.addLoan(loan);
        

        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        

        assertFalse("Book should not be overdue on due date", isOverdue);
        assertTrue("Due date should be today", loan.getDueDate().isEqual(LocalDate.now()));
    }
    
    @Test
    public void testLoan29DaysOverdue() {

    	Loan loan = new Loan(testBook, testUser, 
                           LocalDate.now().minusDays(57), 
                           LocalDate.now().minusDays(29));
        LoanData.addLoan(loan);
        

        boolean isOverdue = loan.getDueDate().isBefore(LocalDate.now());
        

        assertTrue("Book should be overdue", isOverdue);
        assertTrue("Due date should be in past", loan.getDueDate().isBefore(LocalDate.now()));
    }
    
    @Test
    public void testExtendLoanDueDate() {

    	LocalDate originalDueDate = LocalDate.now().plusDays(14);
        Loan loan = new Loan(testBook, testUser, LocalDate.now(), originalDueDate);
        LoanData.addLoan(loan);
        

        LocalDate newDueDate = originalDueDate.plusDays(7);
        loan.setDueDate(newDueDate);
        

        assertEquals("New due date should be extended by 7 days", 
                     originalDueDate.plusDays(7), loan.getDueDate());
        assertTrue("New due date should be after original", 
                   loan.getDueDate().isAfter(originalDueDate));
    }
    
    @Test
    public void testGetActiveLoans() {

    	Loan activeLoan1 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        Loan activeLoan2 = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(14));
        LoanData.addLoan(activeLoan1);
        LoanData.addLoan(activeLoan2);
        

        List<Loan> activeLoans = LoanData.getActiveLoans();
        

        assertFalse("Should have active loans", activeLoans.isEmpty());
        assertEquals("Should have 2 active loans", 2, activeLoans.size());
        
        for (Loan loan : activeLoans) {
            assertNull("Active loan should not have return date", loan.getReturnDate());
        }
    }
    
    @Test
    public void testReturnBook() {

    	Loan loan = new Loan(testBook, testUser, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan);
        

        loan.setReturnDate(LocalDate.now());
        

        assertNotNull("Return date should be set", loan.getReturnDate());
        assertEquals("Return date should be today", LocalDate.now(), loan.getReturnDate());
        
        List<Loan> activeLoans = LoanData.getLoansByUser(testUser.getId());
        assertTrue("Should have no active loans after return", activeLoans.isEmpty());
    }
    
    @Test
    public void testNoActiveLoansForUser() {

    	

    	List<Loan> activeLoans = LoanData.getLoansByUser("NONEXISTENT_USER");
        

    	assertTrue("Should have no active loans for non-existent user", activeLoans.isEmpty());
    }
    
    @Test
    public void testLoanCreation() {

    	LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(28);
        

        Loan loan = new Loan(testBook, testUser, borrowDate, dueDate);
        LoanData.addLoan(loan);
        

        assertNotNull("Loan should be created", loan);
        assertEquals("Book should match", testBook, loan.getBook());
        assertEquals("User should match", testUser, loan.getUser());
        assertEquals("Borrow date should match", borrowDate, loan.getBorrowDate());
        assertEquals("Due date should match", dueDate, loan.getDueDate());
        assertNull("Return date should be null for new loan", loan.getReturnDate());
    }
}