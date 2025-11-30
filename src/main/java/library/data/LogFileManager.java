package library.data;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileManager {

    private static final String FOLDER = "Files";
    private static final String PATH = "Files/log.txt";

    private static void ensureFolder() {
        File f = new File(FOLDER);
        if (!f.exists()) f.mkdirs();
    }

    public static void log(String msg) {
        ensureFolder();

        try (BufferedWriter w = new BufferedWriter(new FileWriter(PATH, true))) {
            String time = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            w.write("[" + time + "] " + msg);
            w.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
