package library.entities;

public class User {
    private String id;
    private String name;
    private String email;
    private double fineBalance;
    private boolean canBorrow;
    private boolean isActive;
    
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.fineBalance = 0.0;
        this.canBorrow = true;
        this.isActive = true;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getFineBalance() { return fineBalance; }
    public boolean canBorrow() { return canBorrow; }
    public boolean isActive() { return isActive; }
    
    public void setFineBalance(double fineBalance) { 
        this.fineBalance = fineBalance; 
    }
    
    public void setCanBorrow(boolean canBorrow) { 
        this.canBorrow = canBorrow; 
    }
    
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
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
}