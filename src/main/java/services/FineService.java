package services;

import library.data.UserData;
import library.entities.User;

public class FineService {
    
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
    
    public void addFine(String userId, double amount) {
        User user = UserData.getUserById(userId);
        if (user != null) {
            user.addFine(amount);
        }
    }
    
    public double getFineBalance(String userId) {
        User user = UserData.getUserById(userId);
        return user != null ? user.getFineBalance() : 0.0;
    }
}