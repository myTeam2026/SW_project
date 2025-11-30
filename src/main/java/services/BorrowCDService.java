package services;

import library.data.CDData;
import library.entities.CD;
import library.entities.Loan;
import library.entities.User;

import java.time.LocalDate;
import java.util.List;

public class BorrowCDService {

    public String borrowCD(String cdId, User user) {

        CD cd = CDData.getCD(cdId);

        if (cd == null) return "Error: CD not found";
        if (user == null) return "Error: User not found";

        if (!cd.isAvailable()) return "Error: CD is not available";

        if (!user.CanBorrow())
            return "Error: User cannot borrow due to restrictions";

        cd.setAvailable(false);
        user.borrowCD(cdId, LocalDate.now());

        // ===============================
        //          SEND EMAIL
        // ===============================

        EmailService emailService = new EmailService();
        emailService.sendEmail(
                user.getEmail(),
                "CD Borrowed Successfully",
                "Hello " + user.getName() + ",\n\n"
                + "You borrowed the CD successfully.\n"
                + "CD ID: " + cdId + "\n"
                + "Title: " + cd.getTitle() + "\n\n"
                + "Enjoy listening!"
        );

        return "Success: CD borrowed successfully";
    }

    public String returnCD(String cdId, User user) {

        CD cd = CDData.getCD(cdId);

        if (cd == null) return "Error: CD not found";

        if (!user.hasBorrowedCD(cdId))
            return "Error: User did not borrow this CD";

        LocalDate dueDate = user.getBorrowedCDDueDate(cdId);

        if (LocalDate.now().isAfter(dueDate))
            user.addFine(20.0);

        cd.setAvailable(true);
        user.returnCD(cdId);

        return "Success: CD returned successfully";
    }

    public List<Loan> getUserCDLoans1(String id) {
        return null;
    }

    public List<Loan> getUserCDLoans(String id) {
        return null;
    }
}
