package library.data;

import library.entities.User;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    private static List<User> users = new ArrayList<>();
    
    static {
        // Sample users for testing
        users.add(new User("U001", "John Doe", "john@email.com"));
        users.add(new User("U002", "Jane Smith", "jane@email.com"));
        users.add(new User("U003", "Alice Johnson", "alice@email.com"));
        users.add(new User("U082", "User Eight Two", "u082@email.com"));
        users.add(new User("UQQ1", "User QQ One", "uqq1@email.com"));
    }
    
    public static User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                return user;
            }
        }
        return null;
    }
    
    public static void addUser(User user) {
        users.add(user);
    }
    
    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public static void clearUsers() {
        users.clear();
        users.add(new User("U001", "John Doe", "john@email.com"));
        users.add(new User("U002", "Jane Smith", "jane@email.com"));
        users.add(new User("U003", "Alice Johnson", "alice@email.com"));
        users.add(new User("U082", "User Eight Two", "u082@email.com"));
        users.add(new User("UQQ1", "User QQ One", "uqq1@email.com"));
    }
}