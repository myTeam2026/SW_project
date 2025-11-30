package library.entities;

import java.io.Serializable;

public class CDLoan implements Serializable {

    private String cdId;
    private String userId;
    private String borrowDate;
    private boolean returned;

    public CDLoan(String cdId, String userId, String borrowDate, boolean returned) {
        this.cdId = cdId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returned = returned;
    }

    public String getCdId() {
        return cdId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

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
