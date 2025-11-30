package library.data;

import java.util.ArrayList;
import java.util.List;
import library.entities.CD;

public class CDData {
    private static List<CD> cds = new ArrayList<>();

    public static void addCD(CD cd) {
        cds.add(cd);
    }

    public static CD getCD(String cdId) {
        for (CD cd : cds) {
            if (cd.getCdId().equalsIgnoreCase(cdId)) {
                return cd;
            }
        }
        return null;
    }
    

    public static List<CD> getAllCDs() {
        return new ArrayList<>(cds);
    }

    public static void clearCDs() {
        cds.clear();
    }

    public static void removeCD(String cdId) {
        CD cdToRemove = null;
        for (CD cd : cds) {
            if (cd.getCdId().equalsIgnoreCase(cdId)) {
                cdToRemove = cd;
                break;
            }
        }
        if (cdToRemove != null) {
            cds.remove(cdToRemove);
        }
    }
}
