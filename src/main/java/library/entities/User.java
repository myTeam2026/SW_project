package library.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;

    private boolean active = true;
    private boolean canBorrow = true;
    private double fineBalance = 0.0;

    private Map<String, LocalDate> borrowedCDs = new HashMap<>();

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = "";
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean CanBorrow() { return canBorrow; }
    public void setCanBorrow(boolean canBorrow) { this.canBorrow = canBorrow; }

    public double getFineBalance() { return fineBalance; }

    public void addFine(double amount) { fineBalance += amount; }

    public void payFine(double amount) {
        fineBalance -= amount;
        if (fineBalance < 0) fineBalance = 0;
    }

    public void setFineBalance(double val) {
        this.fineBalance = Math.max(val, 0);
    }

    public void borrowCD(String cdId, LocalDate date) {
        borrowedCDs.put(cdId, date);
    }

    public boolean hasBorrowedCD(String cdId) {
        return borrowedCDs.containsKey(cdId);
    }

    public LocalDate getBorrowedCDDueDate(String cdId) {
        return borrowedCDs.get(cdId);
    }

    public void returnCD(String cdId) {
        borrowedCDs.remove(cdId);
    }

    public Map<String, LocalDate> getBorrowedCDs() {
        return borrowedCDs;
    }
    public void addBorrowedCD(CD cd, LocalDate date) {
        if (cd != null) {
            borrowedCDs.put(cd.getCdId(), date);
        }
    }

    // Set/update due date for borrowed CD
    public void setBorrowedCDDueDate(String cdId, LocalDate date) {
        borrowedCDs.put(cdId, date);
    }
}
