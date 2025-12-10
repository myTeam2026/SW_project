package services;

import library.data.UserData;
import library.data.LoanData;
import library.data.FineData;
import library.entities.User;

/**
 * Service class responsible for managing user-related operations,
 * such as unregistering users and checking their eligibility.
 * <p>
 * Provides methods to validate administrative permissions,
 * active loans, and unpaid fines before unregistering a user.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class UserService {
    
    /**
     * Unregisters a user if they have no active loans or unpaid fines.
     * Only administrators are allowed to perform this action.
     *
     * @param userId the ID of the user to unregister
     * @param adminId the ID of the administrator performing the action
     * @return a message indicating success or the reason for failure
     */
    public String unregisterUser(String userId, String adminId) {
        if (!isAdmin(adminId)) {
            return "Error: Only administrators can unregister users";
        }
        
        User user = UserData.getUserById(userId);
        if (user == null) {
            return "Error: User not found";
        }
        
        if (hasActiveLoans(userId)) {
            return "Error: Cannot unregister - user has active loans";
        }
        
        if (hasUnpaidFines(userId)) {
            return "Error: Cannot unregister - user has unpaid fines";
        }
        
        user.setActive(false);
        return "Success: User unregistered successfully";
    }
    
    /**
     * Checks if a user can be unregistered.
     * A user can be unregistered only if they have no active loans and no unpaid fines.
     *
     * @param userId the ID of the user
     * @return true if the user can be unregistered, false otherwise
     */
    public boolean canUnregisterUser(String userId) {
        return !hasActiveLoans(userId) && !hasUnpaidFines(userId);
    }
    /**
 * Resets the password of a user.
 * <p>
 * This method verifies the old password before updating it to the new one.
 * Returns a success message if updated successfully, otherwise an error message.
 * </p>
 *
 * @param userId the ID of the user whose password will be reset
 * @param oldPassword the current password of the user
 * @param newPassword the new password to set
 * @return a message indicating success or failure reason
 */
public String resetPassword(String userId, String oldPassword, String newPassword) {
    User user = UserData.getUserById(userId);
    if (user == null) {
        return "Error: User not found";
    }
    
    if (!user.getPassword().equals(oldPassword)) {
        return "Error: Old password is incorrect";
    }
    
    user.setPassword(newPassword);
    return "Success: Password updated successfully";
}

    /**
     * Checks if a given user ID belongs to an administrator.
     *
     * @param adminId the ID of the user to check
     * @return true if the user is an administrator, false otherwise
     */
    private boolean isAdmin(String adminId) {
        User admin = UserData.getUserById(adminId);
        return admin != null && "admin".equals(admin.getId().toLowerCase());
    }
    
    /**
     * Checks if a user has any active loans.
     *
     * @param userId the ID of the user
     * @return true if the user has active loans, false otherwise
     */
    private boolean hasActiveLoans(String userId) {
        return !LoanData.getLoansByUser(userId).isEmpty();
    }
    
    /**
     * Checks if a user has any unpaid fines.
     *
     * @param userId the ID of the user
     * @return true if the user has unpaid fines, false otherwise
     */
    private boolean hasUnpaidFines(String userId) {
        User user = UserData.getUserById(userId);
        return user != null && user.getFineBalance() > 0;
    }
}
