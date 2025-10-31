package services;

import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;
import library.data.FineData;
import library.entities.Book;
import library.entities.User;
import library.entities.Loan;
import java.time.LocalDate;

public class BorrowService {
    
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
    
    public boolean canUserBorrow(String userId) {
        User user = UserData.getUserById(userId);
        return user != null && 
               user.isActive() && 
               user.canBorrow() && 
               user.getFineBalance() == 0 &&
               !hasOverdueBooks(userId);
    }
    
    public boolean hasOverdueBooks(String userId) {
        for (Loan loan : LoanData.getLoansByUser(userId)) {
            if (loan.getDueDate().isBefore(LocalDate.now()) && loan.getReturnDate() == null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasUnpaidFines(String userId) {
        User user = UserData.getUserById(userId);
        return user != null && user.getFineBalance() > 0;
    }
    
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