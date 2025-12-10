package library.entities;

/**
 * Represents a fine imposed on a library user.
 * <p>
 * Stores the fine's ID, associated user, amount, reason, and payment status.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
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

    /**
     * Pays the fine partially or fully.
     * <p>
     * If the paid amount is greater than or equal to the fine, marks it as paid and updates the user's account.
     * If the paid amount is less than the fine, deducts the amount and keeps the fine as unpaid.
     * </p>
     *
     * @param paidAmount the amount paid by the user
     */
    public void payFine(double paidAmount) {
        if (paidAmount >= this.amount) {
            user.payFine(this.amount);
            this.amount = 0.0;
            this.isPaid = true;
        } else {
            user.payFine(paidAmount);
            this.amount -= paidAmount;
            this.isPaid = false;
        }
    }
}
