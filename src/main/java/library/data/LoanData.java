package library.data;

import library.entities.Loan;
import java.util.ArrayList;
import java.util.List;

public class LoanData {

    private static List<Loan> loans = new ArrayList<>();

    public static void addLoan(Loan loan) {
        loans.add(loan);
    }

    public static Loan getLoanById(String loanId) {
        for (Loan loan : loans) {
            if (loan.getLoanId().equals(loanId)) {
                return loan;
            }
        }
        return null;
    }

    // ===== Books + CDs (مشترك) =====
    public static List<Loan> getLoansByUser(String userId) {
        List<Loan> userLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getUser().getId().equals(userId) &&
                loan.getReturnDate() == null) {
                userLoans.add(loan);
            }
        }
        return userLoans;
    }

    // ===== CDs فقط =====
    public static List<Loan> getCDLoansByUser(String userId) {
        List<Loan> cdLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getUser().getId().equals(userId) &&
                loan.getCD() != null &&
                loan.getReturnDate() == null) {

                cdLoans.add(loan);
            }
        }
        return cdLoans;
    }

    // All active loans
    public static List<Loan> getActiveLoans() {
        List<Loan> activeLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) {
                activeLoans.add(loan);
            }
        }
        return activeLoans;
    }

    public static void clearLoans() {
        loans.clear();
    }
}
