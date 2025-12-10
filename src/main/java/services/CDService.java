package services;

import library.entities.CD;
import library.entities.User;
import library.data.UserData;
import library.data.CDData;

import java.time.LocalDate;

public class CDService {

    public boolean exists(String cdId){
        return CDData.getCD(cdId)!=null;
    }

    public String addCD(String title, String artist, String cdId) {
        if (exists(cdId)) return error("CD with this ID already exists");
        CDData.addCD(new CD(cdId, title, artist));
        return ok("CD added successfully");
    }

    public CD searchCDById(String cdId) {
        return CDData.getCD(cdId);
    }

    public String removeCD(String cdId) {
        CD cd = CDData.getCD(cdId);
        if(cd==null) return error("CD not found");
        CDData.removeCD(cdId);
        return ok("CD removed successfully");
    }

    public void applyOverdueFine(CD cd, String userId) {
        User u = UserData.getUserById(userId);
        if(u==null) return;
        LocalDate due = u.getBorrowedCDDueDate(cd.getCdId());
        if(due==null) return;
        if(LocalDate.now().isAfter(due)) u.addFine(20.0);
    }

    public String borrowCD(CD cd, String userId) {
        User u = UserData.getUserById(userId);
        if(u==null) return "Error: User not found";
        if(!u.canBorrow()) return "Error: Cannot borrow - unpaid fines or restrictions";
        if(!cd.isAvailable()) return "Error: CD is not available";
        LocalDate due = LocalDate.now().plusDays(7);
        u.addBorrowedCD(cd,due);
        cd.setAvailable(false);
        return ok("CD borrowed successfully, due "+due);
    }

    public String returnCD(CD cd, String userId) {
        User u = UserData.getUserById(userId);
        if(u==null) return "Error: User not found";
        applyOverdueFine(cd,userId);
        cd.setAvailable(true);
        u.returnCD(cd.getCdId());
        return ok("CD returned");
    }

    public String ok(String msg){
        return "Success: "+msg;
    }

    public String error(String msg){
        return "Error: "+msg;
    }
}
