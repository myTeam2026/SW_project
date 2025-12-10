package library.services;

import library.data.UserData;
import library.entities.User;

/**
 * Service class to manage fines for users in the library system.
 * <p>
 * Provides methods to pay fines, add fines, and check a user's fine balance.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class FineService {

    /**
     * Pays a fine for a user.
     *
     * @param userId the ID of the user paying the fine
     * @param amount the amount to pay
     * @return true if payment was successful, false otherwise
     */
    public boolean payFine(String userId, double amount) {
        User user = UserData.getUserById(userId);
        if (user == null) {
            return false;
        }
        
        if (amount <= 0) {
            return false;
        }
        
        if (amount > user.getFineBalance()) {
            return false;
        }
        
        user.payFine(amount);
        return true;
    }

    /**
     * Adds a fine to a user's account.
     *
     * @param userId the ID of the user to be fined
     * @param amount the fine amount
     */
    public void addFine(String userId, double amount) {
        User user = UserData.getUserById(userId);
        if (user != null) {
            user.addFine(amount);
        }
    }

    /**
     * Retrieves the total fine balance for a user.
     *
     * @param userId the ID of the user
     * @return the user's fine balance; returns 0.0 if user not found
     */
    public double getFineBalance(String userId) {
        User user = UserData.getUserById(userId);
        return user != null ? user.getFineBalance() : 0.0;
    }
}
