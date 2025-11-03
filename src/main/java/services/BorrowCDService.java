/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import library.entities.CD;
import library.entities.User;
import library.data.CDData;

import java.time.LocalDate;

public class BorrowCDService {

    
    public String borrowCD(String cdId, User user) {
        CD cd = CDData.getCD(cdId);
    if (cd == null) return "Error: CD not found";
    if (!cd.isAvailable()) return "Error: CD is not available";
    if (!user.canBorrow()) return "Error: Cannot borrow - unpaid fines or restrictions";

    LocalDate dueDate = LocalDate.now().plusDays(7);
    user.addBorrowedCD(cd, dueDate);
    cd.setAvailable(false);

    return "Success: CD borrowed successfully, due " + dueDate;
}
    

    
    public String returnCD(String cdId, User user) {
        CD cd = CDData.getCD(cdId);
    if (cd == null) return "Error: CD not found";
    if (!user.hasBorrowedCD(cdId)) return "Error: User did not borrow this CD";

    LocalDate dueDate = user.getBorrowedCDDueDate(cdId);
    if (LocalDate.now().isAfter(dueDate)) {
        user.addFine(20.0);
    }

    cd.setAvailable(true);
    user.returnCD(cdId);

    return "Success: CD returned successfully";

        
    }
}

