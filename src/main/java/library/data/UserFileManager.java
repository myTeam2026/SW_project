package library.data;

import library.entities.User;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public final class UserFileManager {

    private static final String FILE_PATH = "Files/users.txt";

    private UserFileManager(){}

    public static String buildFilePath(){
        return FILE_PATH;
    }

    public static boolean exists(){
        return new File(FILE_PATH).exists();
    }

    public static String formatUser(User user){
        return user.getId() + "," +
               user.getName() + "," +
               user.getEmail() + "," +
               user.getPassword();
    }

    public static User parseUser(String line){
        String[] p = line.split(",");
        return new User(p[0],p[1],p[2],p[3]);
    }

    public static void saveUser(User user) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(formatUser(user) + "\n");
        } catch (IOException e) {}
    }
}
