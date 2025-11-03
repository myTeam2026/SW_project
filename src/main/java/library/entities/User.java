package library.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String name;
    private String email;
    private double fineBalance;
    private boolean canBorrow;
    private boolean isActive;

    // خريطة لتخزين CDs المستعارة مع تاريخ الاستحقاق
    private Map<String, LocalDate> borrowedCDs;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.fineBalance = 0.0;
        this.canBorrow = true;
        this.isActive = true;
        this.borrowedCDs = new HashMap<>();
    }

    // -------------------- Getters --------------------
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getFineBalance() { return fineBalance; }
    public boolean canBorrow() { return canBorrow; }
    public boolean isActive() { return isActive; }
    public Map<String, LocalDate> getBorrowedCDs() { return borrowedCDs; } // مهم للتقرير

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

    // -------------------- Fine Management --------------------
    public void addFine(double amount) {
        this.fineBalance += amount;
        if (this.fineBalance > 0) {
            this.canBorrow = false;
        }
    }
    
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
    public void addBorrowedCD(CD cd, LocalDate dueDate) {
        borrowedCDs.put(cd.getCdId(), dueDate);
    }

    public LocalDate getBorrowedCDDueDate(String cdId) {
        return borrowedCDs.get(cdId);
    }

    public void returnCD(String cdId) {
        borrowedCDs.remove(cdId);
    }

    public boolean hasBorrowedCD(String cdId) {
        return borrowedCDs.containsKey(cdId);
    }
    





}
