package library.entities;

import java.io.Serializable;

/**
 * Represents a loan of a CD to a library user.
 * <p>
 * This class stores information about the CD being borrowed, the user who borrowed it,
 * the borrow date, and whether the CD has been returned or not.
 * </p>
 * <p>
 * Implements {@link Serializable} to allow CDLoan objects to be serialized.
 * </p>
 */
public class CDLoan implements Serializable {

    /** The unique identifier of the CD being borrowed. */
    private String cdId;

    /** The unique identifier of the user borrowing the CD. */
    private String userId;

    /** The date when the CD was borrowed. */
    private String borrowDate;

    /** Indicates whether the CD has been returned. */
    private boolean returned;

    /**
     * Constructs a new CDLoan with the specified CD ID, user ID, borrow date, and return status.
     *
     * @param cdId the ID of the CD
     * @param userId the ID of the user
     * @param borrowDate the date the CD was borrowed
     * @param returned true if the CD has been returned, false otherwise
     */
    public CDLoan(String cdId, String userId, String borrowDate, boolean returned) {
        this.cdId = cdId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returned = returned;
    }

    /**
     * Returns the ID of the CD.
     *
     * @return the CD ID
     */
    public String getCdId() {
        return cdId;
    }

    /**
     * Returns the ID of the user who borrowed the CD.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the date when the CD was borrowed.
     *
     * @return the borrow date
     */
    public String getBorrowDate() {
        return borrowDate;
    }

    /**
     * Checks whether the CD has been returned.
     *
     * @return true if returned, false otherwise
     */
    public boolean isReturned() {
        return returned;
    }

    /**
     * Sets the return status of the CD.
     *
     * @param returned true if the CD has been returned, false otherwise
     */
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    /**
     * Returns a string representation of the CDLoan object.
     *
     * @return a string describing the CDLoan
     */
    @Override
    public String toString() {
        return "CDLoan{" +
                "cdId='" + cdId + '\'' +
                ", userId='" + userId + '\'' +
                ", borrowDate='" + borrowDate + '\'' +
                ", returned=" + returned +
                '}';
    }
}
