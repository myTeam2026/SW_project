package library.data;

import library.entities.CD;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class CDFileManager {

    private static final String FILE_PATH = "Files/cds.txt";

    private CDFileManager(){}

    public static String buildFilePath(){
        return FILE_PATH;
    }

    public static boolean exists(){
        return new File(FILE_PATH).exists();
    }

    public static String formatCD(CD cd){
        return cd.getTitle() + "," + cd.getArtist() + "," + cd.getCdId();
    }

    public static CD parseCD(String line){
        String[] p = line.split(",");
        return new CD(p[0],p[1],p[2]);
    }

    public static void saveCDToFile(CD cd) {
        try {
            File f = new File(FILE_PATH);
            f.getParentFile().mkdirs();
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write(formatCD(cd));
            bw.newLine();
            bw.close();
        } catch (Exception e) {}
    }

    public static List<CD> loadCDsFromFile() {
        List<CD> cds = new ArrayList<>();

        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) return cds;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3) cds.add(parseCD(line));
            }
            br.close();
        } catch (Exception e) {}

        return cds;
    }
}
