package services;

import library.data.UserData;
import library.data.LoanData;
import library.entities.User;

public class UserService {

    public String unregisterUser(String userId, String adminId) {
        if (!isAdmin(adminId)) return error("Only administrators can unregister users");
        User user = UserData.getUserById(userId);
        if (user == null) return error("User not found");
        if (hasActiveLoans(userId)) return error("Cannot unregister - user has active loans");
        if (hasUnpaidFines(userId)) return error("Cannot unregister - user has unpaid fines");
        user.setActive(false);
        return ok("User unregistered successfully");
    }

    public boolean canUnregisterUser(String userId) {
        return !hasActiveLoans(userId) && !hasUnpaidFines(userId);
    }

    public String resetPassword(String userId, String oldPassword, String newPassword) {
        User user = UserData.getUserById(userId);
        if (user == null) return error("User not found");
        if (!user.getPassword().equals(oldPassword)) return error("Old password is incorrect");
        user.setPassword(newPassword);
        return ok("Password updated successfully");
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

    public String ok(String m){
        return "Success: "+m;
    }

    public String error(String m){
        return "Error: "+m;
    }
}
