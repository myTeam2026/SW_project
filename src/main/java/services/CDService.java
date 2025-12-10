
 package services;

import library.entities.CD;
import library.entities.User;
import library.data.UserData;
import library.data.CDData;
import library.data.LoanData;
import library.entities.Loan;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class to manage CD borrowing, returning, and overdue fines.
 * <p>
 * Provides methods to borrow CDs, return CDs, and automatically apply fines
 * if a CD is returned after its due date.
 * Also provides add, search, and remove operations for CDs.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class CDService {

    /**
     * Adds a new CD to the system.
     *
     * @param title  the title of the CD
     * @param artist the artist of the CD
     * @param cdId   the unique CD ID
     * @return a success message
     */
    public String addCD(String title, String artist, String cdId) {
        if (CDData.getCD(cdId) != null) {
            return "Error: CD with this ID already exists";
        }
        CD newCD = new CD(cdId, title, artist);
        CDData.addCD(newCD);
        return "Success: CD added successfully";
    }

    /**
     * Searches for a CD by its ID.
     *
     * @param cdId the ID of the CD
     * @return the CD object if found, null otherwise
     */
    public CD searchCDById(String cdId) {
        return CDData.getCD(cdId);
    }

    /**
     * Removes a CD from the system by its ID.
     *
     * @param cdId the ID of the CD to remove
     * @return a success message if removed, error message if not found
     */
  public String removeCD(String cdId) {
    CD cdToRemove = CDData.getCD(cdId);
    if (cdToRemove != null) {
        CDData.removeCD(cdId); // استخدام الميثود الرسمية لإزالة CD
        return "Success: CD removed successfully";
    } else {
        return "Error: CD not found";
    }
}


    // ================= Existing methods =================

    public void applyOverdueFine(CD cd, String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return;

        LocalDate dueDate = user.getBorrowedCDDueDate(cd.getCdId());
        if (dueDate == null) return; // CD not borrowed

        if (LocalDate.now().isAfter(dueDate)) {
            user.addFine(20.0);
        }
    }

    public String borrowCD(CD cd, String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return "Error: User not found";
        if (!user.canBorrow()) return "Error: Cannot borrow - unpaid fines or restrictions";
        if (!cd.isAvailable()) return "Error: CD is not available";

        LocalDate dueDate = LocalDate.now().plusDays(7);
        user.addBorrowedCD(cd, dueDate);
        cd.setAvailable(false);

        return "Success: CD borrowed successfully, due " + dueDate;
    }

    public String returnCD(CD cd, String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return "Error: User not found";

        applyOverdueFine(cd, userId);
        cd.setAvailable(true);
        user.returnCD(cd.getCdId());

        return "Success: CD returned";
    }
}

 
 
 
 
 
 

 
 
 
 
 

 
 


