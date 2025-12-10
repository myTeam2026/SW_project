package library.data;

import library.entities.Loan;
import library.entities.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides static data management for loans in the library system.
 * <p>
 * This class allows adding loans, retrieving loans by ID or user,
 * getting active loans, and clearing all loan data (for testing or reset purposes).
 * </p>
 * 
 * @author Hamsa
 * @version 1.2
 */
public class LoanData {

    /** List that stores all Loan objects in the system. */
    private static List<Loan> loans = new ArrayList<>();
    
    static {
        try {
            // استخدام الـ ID الصحيح كما في UserData
            User hamsaUser = UserData.getUserById("U001");
            if (hamsaUser != null) {
                Loan overdueLoan = new Loan(
                    "L001",
                    hamsaUser,
                    LocalDate.now().minusDays(10),
                    LocalDate.now().minusDays(5),
                    null
                );
                loans.add(overdueLoan);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Failed to initialize LoanData static block: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Adds a new loan to the system. */
    public static void addLoan(Loan loan) {
        loans.add(loan);
    }

    /** Retrieves a loan by its unique ID. */
    public static Loan getLoanById(String loanId) {
        for (Loan loan : loans) {
            if (loan.getLoanId().equals(loanId)) {
                return loan;
            }
        }
        return null;
    }
    
    /** Retrieves all active (not yet returned) loans for a specific user. */
    public static List<Loan> getLoansByUser(String userId) {
        List<Loan> userLoans = new ArrayList<>();
        for (Loan loan : loans) {
           if (loan.getUser().getId().equalsIgnoreCase(userId) && loan.getReturnDate() == null) {
    userLoans.add(loan);
}

        }
        return userLoans;
    }
    
    /** Retrieves all active loans in the system. */
    public static List<Loan> getActiveLoans() {
        List<Loan> activeLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) {
                activeLoans.add(loan);
            }
        }
        return activeLoans;
    }

    /** Clears all loans from the system. */
    public static void clearLoans() {
        loans.clear();
    }
}

