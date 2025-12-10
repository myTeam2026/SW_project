package library.data;

import library.entities.Admin;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides static data for Admin users in the system.
 * Contains a list of Admin objects and methods to access them.
 * <p>
 * This class simulates a simple in-memory database for admin authentication.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class AdminData {

    /**
     * List that stores all Admin users.
     * This list is pre-populated with two sample Admins.
     */
    private static List<Admin> Admins = new ArrayList<>();

    /**
     * Static block to initialize the list of Admins with sample data.
     */
    static {
        Admins.add(new Admin("admin1", "pass123"));
        Admins.add(new Admin("admin2", "adminpass"));
    }

    /**
     * Retrieves an Admin object by its username.
     *
     * @param username the username of the admin to search for
     * @return the Admin object if found; otherwise returns null
     */
    public static Admin getAdminByUsername(String username) {
        for (Admin admin : Admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }
        return null;
    }
}
