package library.data;

import library.entities.User;
import java.io.FileWriter;
import java.io.IOException;

public class UserFileManager {

    private static final String FILE_PATH = "Files/users.txt";

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
