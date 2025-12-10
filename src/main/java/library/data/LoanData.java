package library.data;

import library.entities.Loan;
import library.entities.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class LoanData {

    private static List<Loan> loans = new ArrayList<>();

    static {
        loans.add(
                new Loan(
                        "XX",
                        new User("XX","TestUser","x@x.com","*"),
                        LocalDate.now().minusDays(5),
                        null,
                        null
                )
        );
    }

    private LoanData(){}

    public static List<Loan> buildList(){
        return loans;
    }

    public static int size(){
        return loans.size();
    }

    public static boolean exists(String id){
        return getLoanById(id) != null;
    }

    public static void addLoan(Loan loan) {
        loans.add(loan);
    }

    public static Loan getLoanById(String loanId) {
        for (Loan loan : loans) {
            if (loan.getLoanId().equals(loanId)) return loan;
        }
        return null;
    }

    public static List<Loan> getLoansByUser(String userId) {
        List<Loan> userLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getUser().getId().equalsIgnoreCase(userId) && loan.getReturnDate() == null)
                userLoans.add(loan);
        }
        return userLoans;
    }

    public static List<Loan> getActiveLoans() {
        List<Loan> activeLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) activeLoans.add(loan);
        }
        return activeLoans;
    }

    public static void clearLoans() {
        loans.clear();
    }
}
