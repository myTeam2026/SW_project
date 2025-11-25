package library.entities;

public class Fine {
    private String fineId;
    private User user;
    private double amount;
    private String reason;
    private boolean isPaid;
    
    public Fine(String fineId, User user, double amount, String reason) {
        this.fineId = fineId;
        this.user = user;
        this.amount = amount;
        this.reason = reason;
        this.isPaid = false;
    }
    
    public String getFineId() { return fineId; }
    public User getUser() { return user; }
    public double getAmount() { return amount; }
    public String getReason() { return reason; }
    public boolean isPaid() { return isPaid; }
    
    public void payFine(double amount) {
        if (amount >= this.amount) {
            this.isPaid = true;
            user.payFine(amount);
        }
    }
}