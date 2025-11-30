package services;

import library.data.BookData;
import library.data.UserData;
import library.data.LoanData;

import library.entities.Book;
import library.entities.User;
import library.entities.Loan;

import java.time.LocalDate;
import java.util.List;

public class BorrowService {

    // خدمة إرسال الإيميل
    private EmailService emailService = new EmailService();

    public String borrowBook(String isbn, String userId) {

        Book book = BookData.getBookByISBN(isbn);
        User user = UserData.getUserById(userId);

        if (book == null) return "Error: Book not found";
        if (user == null) return "Error: User not found";

        if (!user.isActive()) return "Error: User account is inactive";
        if (hasOverdueBooks(userId)) return "Error: Cannot borrow - user has overdue books";
        if (hasUnpaidFines(userId)) return "Error: Cannot borrow - user has unpaid fines";

        if (LoanData.getLoansByUser(userId).size() >= 3)
            return "Error: Cannot borrow - maximum limit reached";

        if (!book.isAvailable()) return "Error: Book is not available";

        book.setAvailable(false);

        Loan loan = new Loan(
                book,
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(28)
        );

        LoanData.addLoan(loan);

        // ⭐⭐ إرسال إيميل للمستخدم بعد استعارته كتاب — بدون تغيير أي شيء آخر ⭐⭐
        emailService.sendReminder(
                user.getEmail(),
                "Book Borrowed Successfully",
                "Hello " + user.getName() + ",\n\n"
                + "You have borrowed the following book:\n"
                + "Title: " + book.getTitle() + "\n"
                + "ISBN: " + book.getISBN() + "\n"
                + "Due Date: " + loan.getDueDate() + "\n\n"
                + "Enjoy your reading!"
        );
        EmailService emailService = new EmailService();
        emailService.sendEmail(
            user.getEmail(),
            "Book Borrowed Successfully",
            "Hello " + user.getName() +
            "\n\nYou borrowed the book: " + book.getTitle() +
            "\nAuthor: " + book.getAuthor() +
            "\nDue Date: " + loan.getDueDate() +
            "\n\nHappy reading!"
        );

        return "Success: Book borrowed successfully";
    }

    public boolean canUserBorrow(String userId) {
        User user = UserData.getUserById(userId);
        return user != null &&
                user.isActive() &&
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

    public List<Loan> getUserLoans(String userId) {
        return LoanData.getLoansByUser(userId);
    }
}
