package library.services;

import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class to manage borrowing and returning of books in the library.
 * <p>
 * Provides methods to borrow a book, check if a user can borrow, check for overdue books,
 * unpaid fines, and handle returning of books.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class BorrowService {

    /**
     * Borrows a book for a user if all borrowing conditions are met.
     * <p>
     * Conditions include: book availability, user activity, overdue books, unpaid fines,
     * and maximum borrowing limit (3 books).
     * </p>
     *
     * @param isbn   the ISBN of the book to borrow
     * @param userId the ID of the user borrowing the book
     * @return a message indicating success or the reason for failure
     */
    public String borrowBook(String isbn, String userId) {
        Book book = BookData.getBookByISBN(isbn);
        User user = UserData.getUserById(userId);
        
        if (book == null) {
            return "Error: Book not found";
        }
        
        if (user == null) {
            return "Error: User not found";
        }
        
        if (!user.isActive()) {
            return "Error: User account is inactive";
        }
        
        if (hasOverdueBooks(userId)) {
            return "Error: Cannot borrow - user has overdue books";
        }
        
        if (hasUnpaidFines(userId)) {
            return "Error: Cannot borrow - user has unpaid fines";
        }
        
        if (LoanData.getLoansByUser(userId).size() >= 3) {
            return "Error: Cannot borrow - maximum limit reached";
        }
        
        if (!book.isAvailable()) {
            return "Error: Book is not available";
        }
        
        book.setAvailable(false);
        Loan loan = new Loan(book, user, LocalDate.now(), LocalDate.now().plusDays(28));
        LoanData.addLoan(loan);
        
        return "Success: Book borrowed successfully";
    }
    /**
 * Retrieves all loans (borrowed books) for a specific user.
 *
 * @param userId the ID of the user
 * @return a list of Loan objects for the user, or an empty list if none
 */
public List<Loan> getUserLoans(String userId) {
    return LoanData.getLoansByUser(userId);
}


    /**
     * Checks if a user is eligible to borrow books.
     *
     * @param userId the ID of the user
     * @return true if the user can borrow, false otherwise
     */
    public boolean canUserBorrow(String userId) {
        User user = UserData.getUserById(userId);
        return user != null && 
               user.isActive() && 
               user.canBorrow() && 
               user.getFineBalance() == 0 &&
               !hasOverdueBooks(userId);
    }

    /**
     * Checks if the user has any overdue books.
     *
     * @param userId the ID of the user
     * @return true if the user has overdue books, false otherwise
     */
    public boolean hasOverdueBooks(String userId) {
        for (Loan loan : LoanData.getLoansByUser(userId)) {
            if (loan.getDueDate().isBefore(LocalDate.now()) && loan.getReturnDate() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the user has any unpaid fines.
     *
     * @param userId the ID of the user
     * @return true if the user has unpaid fines, false otherwise
     */
    public boolean hasUnpaidFines(String userId) {
        User user = UserData.getUserById(userId);
        return user != null && user.getFineBalance() > 0;
    }

    /**
     * Returns a borrowed book.
     * <p>
     * Marks the book as available and sets the return date in the loan record.
     * </p>
     *
     * @param loanId the ID of the loan
     * @return true if the book was successfully returned, false if loan not found
     */
    
    public boolean returnBook(String loanId) {
        Loan loan = LoanData.getLoanById(loanId);
        if (loan != null) {
            loan.getBook().setAvailable(true);
            loan.setReturnDate(LocalDate.now());
            return true;
        }
        return false;
    }
}
