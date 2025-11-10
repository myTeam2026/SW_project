package services;

import library.data.CDData;
import library.entities.CD;
import library.entities.User;
import java.time.LocalDate;

public class CDService {

    public String addCD(String title, String artist, String cdId) {
        CD existingCD = CDData.getCD(cdId);
        if (existingCD != null) {
            return "Error: CD with this ID already exists";
        }
        CD newCD = new CD(title, artist, cdId);
        newCD.setAvailable(true);
        CDData.addCD(newCD);
        return "Success: CD added successfully";
    }

    public CD searchCDById(String cdId) {
        return CDData.getCD(cdId);
    }

    public String removeCD(String cdId) {
        CD cd = CDData.getCD(cdId);
        if (cd == null) {
            return "Error: CD not found";
        }
        CDData.removeCD(cdId);
        return "Success: CD removed successfully";
    }

    public String returnCD(CD cd, String userId) {
        if (cd == null) {
            return "Error: CD not found";
        }

        User user = library.data.UserData.getUser(userId);
        if (user == null) {
            return "Error: User not found";
        }

        if (!user.hasBorrowedCD(cd.getCdId())) {
            return "Error: User did not borrow this CD";
        }

        LocalDate dueDate = user.getBorrowedCDDueDate(cd.getCdId());
        if (LocalDate.now().isAfter(dueDate)) {
            user.addFine(20.0);
        }

        cd.setAvailable(true);
        user.returnCD(cd.getCdId());

        return "Success: CD returned successfully";
    }
}
