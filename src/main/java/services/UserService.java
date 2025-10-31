package services;

import library.data.UserData;
import library.data.LoanData;
import library.data.FineData;
import library.entities.User;

public class UserService {
    
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
    
    public boolean canUnregisterUser(String userId) {
        return !hasActiveLoans(userId) && !hasUnpaidFines(userId);
    }
    
    private boolean isAdmin(String adminId) {
        User admin = UserData.getUserById(adminId);
        return admin != null && "admin".equals(admin.getId().toLowerCase());
    }
    
    private boolean hasActiveLoans(String userId) {
        return !LoanData.getLoansByUser(userId).isEmpty();
    }
    
    private boolean hasUnpaidFines(String userId) {
        User user = UserData.getUserById(userId);
        return user != null && user.getFineBalance() > 0;
    }
}