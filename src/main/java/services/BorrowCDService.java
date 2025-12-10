package services;

import library.entities.CD;
import library.entities.User;
import library.data.CDData;
import library.data.LoanData;
import library.entities.Loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing CD borrowing and returning operations
 * within the library system.
 *
 * <p>This class handles borrowing validation (availability, user eligibility),
 * assigns due dates, applies fines for late returns, and retrieves the list
 * of CD loans for a user.</p>
 * 
 * @author Hamsa
 * @version 1.2
 */
public class BorrowCDService {

    /**
     * Default constructor for BorrowCDService.
     */
    public BorrowCDService() {
        // No special initialization required
    }

    /**
     * Attempts to borrow a CD for a given user.
     *
     * <p>The method validates the following conditions:
     * <ul>
     *     <li>User existence</li>
     *     <li>CD existence</li>
     *     <li>CD availability</li>
     *     <li>User borrowing eligibility</li>
     * </ul>
     * If successful, the CD is marked as unavailable, and the user is assigned
     * a due date 7 days from the current date.</p>
     *
     * @param cdId the ID of the CD to be borrowed
     * @param user the {@link User} requesting the CD
     * @return a message describing the result: "Success" or specific error
     */
    public String borrowCD(String cdId, User user) {
        if (user == null) return "Error: User not found";

        CD cd = CDData.getCD(cdId);
        if (cd == null) return "Error: CD not found";
        if (!cd.isAvailable()) return "Error: CD is not available";
        if (!user.canBorrow()) return "Error: User cannot borrow due to restrictions";

        LocalDate dueDate = LocalDate.now().plusDays(7);
        user.addBorrowedCD(cd, dueDate);
        cd.setAvailable(false);

        return "Success: CD borrowed successfully";
    }

    /**
     * Returns a CD previously borrowed by a user.
     *
     * <p>The method checks user existence, CD existence, and whether
     * the CD was actually borrowed by the user. A fine of 20.0 is applied
     * if the CD is returned late.</p>
     *
     * @param cdId the ID of the CD to return
     * @param user the {@link User} returning the CD
     * @return a message describing the result: "Success" or specific error
     */
    public String returnCD(String cdId, User user) {
        if (user == null) return "Error: User not found";

        CD cd = CDData.getCD(cdId);
        if (cd == null) return "Error: CD not found";
        if (!user.hasBorrowedCD(cdId)) return "Error: User did not borrow this CD";

        LocalDate dueDate = user.getBorrowedCDDueDate(cdId);
        if (LocalDate.now().isAfter(dueDate)) {
            user.addFine(20.0);
        }

        cd.setAvailable(true);
        user.returnCD(cdId);

        return "Success: CD returned successfully";
    }

    /**
     * Retrieves the list of CD loans associated with a specific user.
     *
     * <p>This method filters out non-CD loans, ensuring that the returned list
     * contains only CD-related {@link Loan} objects.</p>
     *
     * @param userId the ID of the user whose CD loans are requested
     * @return a list of {@link Loan} objects representing CD loans
     */
    public List<Loan> getUserCDLoans(String userId) {
        List<Loan> allLoans = LoanData.getLoansByUser(userId);
        List<Loan> cdLoans = new ArrayList<>();

        for (Loan loan : allLoans) {
            if (loan.getCD() != null) {  // Assuming Loan class has getCD() method
                cdLoans.add(loan);
            }
        }

        return cdLoans;
    }
}
