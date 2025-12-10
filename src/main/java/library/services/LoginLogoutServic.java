package library.services;

import library.data.AdminData;
import library.entities.Admin;

/**
 * Service class responsible for handling administrator login and logout operations.
 * <p>
 * Allows admins to log in using their username and password and to log out.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class LoginLogoutServic {

    /**
     * Logs in an administrator if the username and password match.
     *
     * @param username the admin's username
     * @param password the admin's password
     * @return true if login is successful; false otherwise
     */
    public boolean login(String username, String password) {
        Admin admin = AdminData.getAdminByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            admin.setLoggedIn(true);
            return true;
        }
        return false;
    }

    /**
     * Logs out an administrator by username.
     *
     * @param username the admin's username
     */
    public void logout(String username) {
        Admin admin = AdminData.getAdminByUsername(username);
        if (admin != null) {
            admin.setLoggedIn(false);
        }
    }
}

