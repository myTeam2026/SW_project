package library.entities;

/**
 * Represents an administrator in the library system.
 * <p>
 * This class stores the admin's username, password, and login status.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class Admin {

    /**
     * The username of the admin.
     */
    private String username;

    /**
     * The password of the admin.
     */
    private String password;

    /**
     * Indicates whether the admin is currently logged in.
     */
    private boolean loggedIn;

    /**
     * Constructs a new Admin with the specified username and password.
     * The initial login status is set to false.
     *
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
    }

    /**
     * Returns the username of the admin.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the admin.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks whether the admin is currently logged in.
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the login status of the admin.
     *
     * @param loggedIn the login status to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
