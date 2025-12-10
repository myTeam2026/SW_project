package library.data;

import library.entities.CD;
import java.util.ArrayList;
import java.util.List;

/**
 * Acts as an in-memory CD database.
 * Completely clean and predictable for unit testing.
 */
public final class CDData {

    // منع إنشاء أي كائن من هذا الكلاس
    private CDData() {}

    // Internal list storing CDs
    private static final List<CD> cds = new ArrayList<>();

    /**
     * Returns all CDs.
     *
     * @return list of CDs
     */
    public static List<CD> getAllCDs() {
        return new ArrayList<>(cds);
    }

    /**
     * Adds a CD to the system.
     *
     * @param cd the CD to add
     */
    public static void addCD(CD cd) {
        cds.add(cd);
    }

    /**
     * Gets a CD by ID.
     *
     * @param cdId ID of the CD
     * @return CD object or null
     */
    public static CD getCD(String cdId) {
        for (CD cd : cds) {
            if (cd.getCdId().equalsIgnoreCase(cdId)) {
                return cd;
            }
        }
        return null;
    }

    /**
     * Removes a CD by ID.
     *
     * @param cdId the CD ID
     */
    public static void removeCD(String cdId) {
        cds.removeIf(cd -> cd.getCdId().equalsIgnoreCase(cdId));
    }

    /**
     * Clears all CDs (for unit testing).
     */
    public static void clearCDs() {
        cds.clear();
    }
}
