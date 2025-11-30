package library.data;

import library.entities.CD;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CDFileManager {

    private static final String FILE_PATH = "Files/cds.txt";

    // حفظ CD جديد داخل الملف
    public static void saveCDToFile(CD cd) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();  // ينشئ مجلد Files إذا مش موجود

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(cd.getTitle() + "," + cd.getArtist() + "," + cd.getCdId());
            bw.newLine();

            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving CD: " + e.getMessage());
        }
    }

    // قراءة جميع الـ CDs من الملف
    public static List<CD> loadCDsFromFile() {
        List<CD> cds = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return cds; // لو ما فيه ملف، رجّع List فاضية
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
