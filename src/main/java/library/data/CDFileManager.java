package library.data;

import library.entities.CD;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides file-based storage and loading functionality for CD objects.
 * <p>
 * This class saves CDs into a text file using CSV format, and retrieves all
 * stored CDs when needed. Each CD is stored as:
 * <br><code>title,artist,cdId</code>
 * </p>
 *
 * @version 1.1
 * @author Hamsa
 */
public class CDFileManager {

    /** Path to the text file where CDs are stored. */
    private static final String FILE_PATH = "Files/cds.txt";

    /**
     * Saves a new CD to the file.
     * <p>
     * If the file or parent folder does not exist, they are created automatically.
     * The CD is written in CSV format.
     * </p>
     *
     * @param cd the CD object to save
     */
    public static void saveCDToFile(CD cd) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(cd.getTitle() + "," + cd.getArtist() + "," + cd.getCdId());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving CD: " + e.getMessage());
        }
    }

    /**
     * Loads all CDs stored in the file.
     * <p>
     * If the file does not exist, an empty list is returned.
     * Each valid line is converted into a {@link CD} object.
     * </p>
     *
     * @return a list containing all CDs loaded from the file
     */
    public static List<CD> loadCDsFromFile() {
        List<CD> cds = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return cds;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3) {
                    cds.add(new CD(p[0], p[1], p[2]));
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error reading CDs: " + e.getMessage());
        }

        return cds;
    }
}
