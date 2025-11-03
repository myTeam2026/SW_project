package services;

import library.entities.CD;
import library.entities.User;
import library.data.UserData;
import library.data.LoanData;
import java.time.LocalDate;

public class CDService {

    // إضافة غرامة تلقائية على CD متأخر
    public void applyOverdueFine(CD cd, String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return;

        LocalDate dueDate = user.getBorrowedCDDueDate(cd.getCdId());
        if (dueDate == null) return; // لم يستعر CD

        if (LocalDate.now().isAfter(dueDate)) {
            // فرض الغرامة: 20 NIS للـCD المتأخر
            user.addFine(20.0);
        }
    }

    // استعارة CD
    public String borrowCD(CD cd, String userId) {
         User user = UserData.getUserById(userId);
    if (user == null) return "Error: User not found";
    if (!user.canBorrow()) return "Error: Cannot borrow - unpaid fines or restrictions";
    if (!cd.isAvailable()) return "Error: CD is not available";

    LocalDate dueDate = LocalDate.now().plusDays(7);
    user.addBorrowedCD(cd, dueDate);
    cd.setAvailable(false); // تحديث حالة CD

    return "Success: CD borrowed successfully, due " + dueDate;
    }

    // إرجاع CD
    public String returnCD(CD cd, String userId) {
        User user = UserData.getUserById(userId);
    if (user == null) return "Error: User not found";

    applyOverdueFine(cd, userId);
    cd.setAvailable(true);       // تحديث حالة CD
    user.returnCD(cd.getCdId());

    return "Success: CD returned";}
       
}
