package library.data;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles writing log messages to a text file for the library system.
 * <p>
 * This class creates the log folder if missing, and appends timestamped
 * messages to <code>log.txt</code>. Each log entry follows the format:
 * <br>
 * <code>[yyyy-MM-dd HH:mm:ss] message</code>
 * </p>
 *
 * @author Hamsa
 * @version 1.1
 */
public class LogFileManager {

    /** Folder in which log files are stored. */
    private static final String FOLDER = "Files";

    /** Path to the log file used for writing log entries. */
    private static final String PATH = "Files/log.txt";

    /**
     * Ensures that the log folder exists.
     * <p>
     * If the folder does not exist, it will be created automatically.
     * </p>
     */
    private static void ensureFolder() {
        File f = new File(FOLDER);
        if (!f.exists()) f.mkdirs();
    }

    /**
     * Writes a log message to the log file with a timestamp.
     * <p>
     * The format of each logged line is:
     * <br>
     * <code>[yyyy-MM-dd HH:mm:ss] message</code>
     * </p>
     *
     * @param msg the message to be recorded in the log file
     */
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
