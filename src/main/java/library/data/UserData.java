package library.data;

import library.entities.User;
import java.util.ArrayList;
import java.util.List;

public class UserData {

    private static final List<User> users = new ArrayList<>();

    public static void clearUsers() {
        users.clear();
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static User getUserById(String id) {
        for (User u : users) {
            if (u.getId().equalsIgnoreCase(id)) return u;
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    public static String generateNewUserId() {
        return "U" + String.format("%03d", users.size() + 1);
    }
}
