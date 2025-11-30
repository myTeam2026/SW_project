package services;

import library.data.UserData;
import library.data.LoanData;
import library.entities.User;
import library.entities.Loan;

public class UserService {

    public User registerUser(String name, String email, String password) {
        String newId = "U" + String.format("%03d", UserData.getAllUsers().size() + 1);
        User user = new User(newId, name, email);
        user.setPassword(password);
        UserData.addUser(user);
        return user;
    }

    public String resetPassword(String userId, String oldPass, String newPass) {
        User u = UserData.getUserById(userId);
        if (u == null) return "User not found.";
        if (!u.getPassword().equals(oldPass)) return "Old password incorrect.";
        u.setPassword(newPass);
        return "Password updated successfully.";
    }

    public boolean canUnregisterUser(String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return false;
        if (!LoanData.getLoansByUser(userId).isEmpty()) return false;
        if (user.getFineBalance() > 0) return false;
        return true;
    }

    public String unregisterUser(String userId, String adminId) {

        if (!adminId.equalsIgnoreCase("admin"))
            return "Error: Only administrators can unregister users";

        User user = UserData.getUserById(userId);
        if (user == null)
            return "Error: User not found";

        if (!LoanData.getLoansByUser(userId).isEmpty())
            return "Error: Cannot unregister - user has active loans";

        if (user.getFineBalance() > 0)
            return "Error: Cannot unregister - user has unpaid fines";

        user.setActive(false);
        return "Success: User unregistered successfully";
    }
    public User registerUser1(String name, String email, String password) {
        String newId = "U" + String.format("%03d", UserData.getAllUsers().size() + 1);
        User user = new User(newId, name, email);
        user.setPassword(password);

        UserData.addUser(user);

        // NEW: send welcome email
        EmailService emailService = new EmailService();
        emailService.sendEmail(
                email,
                "Welcome to AH Library",
                "Hello " + name + ",\n\n"
                + "Your account has been created.\n"
                + "Your User ID: " + newId + "\n\n"
                + "Welcome to the library system!"
        );

        return user;
    }

    
}
