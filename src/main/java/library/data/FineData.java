package library.data;

import library.entities.Fine;
import library.entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides static data management for fines in the library system.
 * <p>
 * This class allows creation of fines for users, retrieval of fines,
 * calculation of total fines per user, and clearing all fines (for testing or reset purposes).
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class FineData {

    /**
     * List that stores all Fine objects in the system.
     */
    private static List<Fine> fines = new ArrayList<>();

    /**
     * Counter to generate unique fine IDs.
     */
    private static int fineCounter = 1;

    /**
     * Creates a new Fine for a given user.
     *
     * @param user   the User object who receives the fine
     * @param amount the amount of the fine
     * @param reason the reason for the fine
     * @return the created Fine object
     */
    public static Fine createFine(User user, double amount, String reason) {
        String fineId = "F" + String.format("%03d", fineCounter++);
        Fine fine = new Fine(fineId, user, amount, reason);
        fines.add(fine);
        user.addFine(amount);
        return fine;
    }

    /**
     * Retrieves all unpaid fines for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of unpaid Fine objects for the user
     */
    public static List<Fine> getFinesByUser(String userId) {
        List<Fine> userFines = new ArrayList<>();
        for (Fine fine : fines) {
            if (fine.getUser().getId().equalsIgnoreCase(userId) && !fine.isPaid()) {
                userFines.add(fine);
            }
        }
        return userFines;
    }

    /**
     * Calculates the total unpaid fines for a specific user.
     *
     * @param userId the ID of the user
     * @return the total amount of unpaid fines
     */
    public static double getTotalFinesByUser(String userId) {
        double total = 0;
        for (Fine fine : fines) {
            if (fine.getUser().getId().equalsIgnoreCase(userId) && !fine.isPaid()) {
                total += fine.getAmount();
            }
        }
        return total;
    }

    /**
     * Clears all fines and resets the fine counter.
     * Typically used for testing or resetting the system.
     */
    public static void clearFines() {
        fines.clear();
        fineCounter = 1;
    }
}
