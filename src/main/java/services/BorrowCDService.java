package services;

import library.entities.CD;
import library.entities.User;
import library.data.CDData;
import library.data.LoanData;
import library.entities.Loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowCDService {

    public String borrowCD(String cdId, User user) {
        if (user == null) return "Error: User not found";
        CD cd = CDData.getCD(cdId);
        if (cd == null) return "Error: CD not found";
        if (!cd.isAvailable()) return "Error: CD is not available";
        if (!user.canBorrow()) return "Error: User cannot borrow due to restrictions";
        LocalDate due = LocalDate.now().plusDays(7);
        user.addBorrowedCD(cd,due);
        cd.setAvailable(false);
        return "Success: CD borrowed successfully";
    }

    public String returnCD(String cdId, User user) {
        if (user == null) return "Error: User not found";
        CD cd = CDData.getCD(cdId);
        if (cd == null) return "Error: CD not found";
        if (!user.hasBorrowedCD(cdId)) return "Error: User did not borrow this CD";
        LocalDate due = user.getBorrowedCDDueDate(cdId);
        if (LocalDate.now().isAfter(due)) user.addFine(20.0);
        cd.setAvailable(true);
        user.returnCD(cdId);
        return "Success: CD returned successfully";
    }

    public List<Loan> getUserCDLoans(String userId) {
        List<Loan> all = LoanData.getLoansByUser(userId);
        List<Loan> out = new ArrayList<>();
        for(Loan l:all) if(l.getCD()!=null) out.add(l);
        return out;
    }
}
