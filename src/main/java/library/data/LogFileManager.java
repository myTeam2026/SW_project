package library.data;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LogFileManager {

    private static final String FOLDER = "Files";
    private static final String PATH = "Files/log.txt";

    private LogFileManager(){}

    public static String buildFolder(){
        return FOLDER;
    }

    public static String buildFilePath(){
        return PATH;
    }

    public static boolean exists(){
        return new File(PATH).exists();
    }

    public static String formatMessage(String msg, String time){
        return "[" + time + "] " + msg;
    }

    public static String currentTimestamp(){
        return LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

    private static void ensureFolder() {
        File f = new File(FOLDER);
        if (!f.exists()) f.mkdirs();
    }

    public static void log(String msg) {
        ensureFolder();
        try (BufferedWriter w = new BufferedWriter(new FileWriter(PATH, true))) {
            String t = currentTimestamp();
            w.write(formatMessage(msg,t));
            w.newLine();
        } catch (Exception e) {}
    }
}
