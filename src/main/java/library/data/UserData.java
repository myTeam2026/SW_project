package library.data;

import library.entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides static data management for users in the library system.
 * <p>
 * This class simulates an in-memory database of users and provides
 * methods to add, retrieve, list, and reset user data.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class UserData {

    /**
     * List that stores all User objects.
     * Pre-populated with sample users in the static block.
     */
    private static List<User> users = new ArrayList<>();

    /**
     * Static block to initialize the users list with sample data.
     */
    static {
        users.add(new User("U001", "John Doe", "john@email.com"));
        users.add(new User("U002", "Jane Smith", "jane@email.com"));
        users.add(new User("U003", "Alice Johnson", "alice@email.com"));
        users.add(new User("U082", "User Eight Two", "u082@email.com"));
        users.add(new User("UQQ1", "User QQ One", "uqq1@email.com"));
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId the ID of the user to search for
     * @return the User object if found; otherwise null
     */
    public static User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a new user to the system.
     *
     * @param user the User object to add
     */
    public static void addUser(User user) {
        users.add(user);
    }

    /**
     * Retrieves a copy of all users in the system.
     *
     * @return a list of all User objects
     */
    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Clears all users and resets the list to the initial sample users.
     * Typically used for testing or resetting the system.
     */
    public static void clearUsers() {
        users.clear();
        users.add(new User("U001", "John Doe", "john@email.com"));
        users.add(new User("U002", "Jane Smith", "jane@email.com"));
        users.add(new User("U003", "Alice Johnson", "alice@email.com"));
        users.add(new User("U082", "User Eight Two", "u082@email.com"));
        users.add(new User("UQQ1", "User QQ One", "uqq1@email.com"));
    }
}
