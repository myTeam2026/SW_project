package library.data;

import library.entities.User;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles saving user information into a text file.
 * <p>
 * This class appends user data to <code>users.txt</code> in CSV format:
 * <br>
 * <code>id,name,email,password</code>
 * </p>
 *
 * @version 1.1
 * @author Hamsa
 */
public class UserFileManager {

    /** Path to the file where user information is stored. */
    private static final String FILE_PATH = "Files/users.txt";

    /**
     * Saves a new user to the users file.
     * <p>
     * The user is written in CSV format and appended to the file.
     * </p>
     *
     * @param user the User object to be saved
     */
    public static void saveUser(User user) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(user.getId() + "," +
                     user.getName() + "," +
                     user.getEmail() + "," +
                     user.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
