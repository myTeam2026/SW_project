package library.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a library user.
 *
 * <p>This class stores the user's ID, name, email, fine balance, borrowing permissions,
 * account status, and borrowed CDs with their due dates.</p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class User {

    /**
     * The unique ID of the user.
     */
    private String id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The current fine balance of the user.
     */
    private double fineBalance;

    /**
     * Indicates whether the user can borrow items.
     */
    private boolean canBorrow;

    /**
     * Indicates whether the user account is active.
     */
    private boolean isActive;

    /**
     * Map storing borrowed CDs and their respective due dates.
     */
    private Map<String, LocalDate> borrowedCDs;

    /**
     * The password of the user for authentication.
     */
    private String password;

    // -------------------- Constructors --------------------

    /**
     * Constructs a new User with the specified ID, name, and email.
     * Initializes fine balance to 0, allows borrowing, sets account as active,
     * and initializes an empty borrowed CD map.
     *
     * @param id    the user's ID
     * @param name  the user's name
     * @param email the user's email
     */
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.fineBalance = 0.0;
        this.canBorrow = true;
        this.isActive = true;
        this.borrowedCDs = new HashMap<>();
    }

    /**
     * Constructs a new User with the specified ID, name, email, and password.
     *
     * @param id       the user's ID
     * @param name     the user's name
     * @param email    the user's email
     * @param password the user's password
     */
    public User(String id, String name, String email, String password) {
        this(id, name, email);
        this.password = password;
    }

    // -------------------- Getters --------------------

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getFineBalance() {
        return fineBalance;
    }

    public boolean canBorrow() {
        return canBorrow;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Returns a map of borrowed CDs and their due dates.
     *
     * @return the borrowed CDs map
     */
    public Map<String, LocalDate> getBorrowedCDs() {
        return borrowedCDs;
    }

    /**
     * Returns the user's password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    // -------------------- Setters --------------------

    public void setFineBalance(double fineBalance) {
        this.fineBalance = fineBalance;
    }

    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets or updates the due date for a borrowed CD.
     *
     * @param cdId    the CD ID
     * @param dueDate the new due date
     */
    public void setBorrowedCDDueDate(String cdId, LocalDate dueDate) {
        borrowedCDs.put(cdId, dueDate);
    }

    // -------------------- Fine Management --------------------

    /**
     * Adds a fine to the user's account and updates borrowing permissions.
     *
     * @param amount the fine amount to add
     */
    public void addFine(double amount) {
        this.fineBalance += amount;
        if (this.fineBalance > 0) {
            this.canBorrow = false;
        }
    }

    /**
     * Pays a specified amount towards the user's fines and updates borrowing permissions.
     *
     * @param amount the amount to pay
     */
    public void payFine(double amount) {
        if (amount <= 0) return;

        if (amount > this.fineBalance) {
            amount = this.fineBalance;
        }

        this.fineBalance -= amount;
        if (this.fineBalance <= 0) {
            this.fineBalance = 0;
            this.canBorrow = true;
        }
    }

    // -------------------- CD Borrowing Management --------------------

    /**
     * Adds a borrowed CD with its due date to the user's account.
     *
     * @param cd      the borrowed CD
     * @param dueDate the due date for returning the CD
     */
    public void addBorrowedCD(CD cd, LocalDate dueDate) {
        borrowedCDs.put(cd.getCdId(), dueDate);
    }

    /**
     * Returns the due date for a borrowed CD.
     *
     * @param cdId the CD ID
     * @return the due date, or null if CD is not borrowed
     */
    public LocalDate getBorrowedCDDueDate(String cdId) {
        return borrowedCDs.get(cdId);
    }

    /**
     * Removes a returned CD from the user's borrowed CDs.
     *
     * @param cdId the CD ID
     */
    public void returnCD(String cdId) {
        borrowedCDs.remove(cdId);
    }

    /**
     * Checks if the user has borrowed a specific CD.
     *
     * @param cdId the CD ID
     * @return true if the CD is borrowed, false otherwise
     */
    public boolean hasBorrowedCD(String cdId) {
        return borrowedCDs.containsKey(cdId);
    }
}
