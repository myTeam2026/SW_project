package services;

import library.data.CDData;
import library.data.CDFileManager;
import library.data.LogFileManager;
import library.entities.CD;
import library.entities.User;
import java.time.LocalDate;

public class CDService {

    public String addCD(String title, String artist, String cdId) {
        CD existingCD = CDData.getCD(cdId);
        if (existingCD != null) {
        	
            LogFileManager.log("ADD_CD_FAILED: CD_ID=" + cdId + " already exists");
            return "Error: CD with this ID already exists";
        }
        CD newCD = new CD(title, artist, cdId);
        newCD.setAvailable(true);
        CDData.addCD(newCD);

        CDFileManager.saveCDToFile(newCD);
        LogFileManager.log("ADD_CD_SUCCESS: CD_ID=" + cdId + ", TITLE=" + title);

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
        LogFileManager.log("REMOVE_CD: CD_ID=" + cdId);
        return "Success: CD removed successfully";
    }

    public String returnCD(CD cd, String userId) {
        if (cd == null) {
            return "Error: CD not found";
        }

        User user = library.data.UserData.getUserById(userId);
        if (user == null) {
            return "Error: User not found";
        }

        if (!user.hasBorrowedCD(cd.getCdId())) {
            return "Error: User did not borrow this CD";
        }

        LocalDate dueDate = user.getBorrowedCDDueDate(cd.getCdId());
        if (LocalDate.now().isAfter(dueDate)) {
            user.addFine(20.0);
            LogFileManager.log("CD_LATE_RETURN_FINE: USER=" + userId + ", CD_ID=" + cd.getCdId());
        }

        cd.setAvailable(true);
        user.returnCD(cd.getCdId());

        LogFileManager.log("CD_RETURN_SUCCESS: USER=" + userId + ", CD_ID=" + cd.getCdId());
        return "Success: CD returned successfully";
    }
}
