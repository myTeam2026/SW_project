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

    private CDLoan(){}

    public static CDLoan build(String cdId, String userId, String borrowDate){
        return new CDLoan(cdId,userId,borrowDate,false);
    }

    public static CDLoan parse(String line){
        String[] p = line.split(",");
        return new CDLoan(p[0],p[1],p[2],Boolean.parseBoolean(p[3]));
    }

    public String format(){
        return cdId + "," + userId + "," + borrowDate + "," + returned;
    }

    public void toggleReturned(){
        this.returned = !this.returned;
    }

    public void setBorrowDate(String d){
        this.borrowDate = d;
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

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof CDLoan)) return false;
        CDLoan c = (CDLoan)o;
        return cdId.equals(c.cdId);
    }

    @Override
    public int hashCode(){
        return cdId.hashCode();
    }
}
