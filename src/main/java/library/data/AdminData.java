package library.data;

import library.entities.Admin;
import java.util.ArrayList;
import java.util.List;

public final class AdminData {

    private static List<Admin> Admins = new ArrayList<>();

    static {
        Admins.add(new Admin("admin1", "pass123"));
        Admins.add(new Admin("admin2", "adminpass"));
    }

    private AdminData(){}

    public static List<Admin> buildList(){
        return Admins;
    }

    public static int size(){
        return Admins.size();
    }

    public static boolean exists(String username){
        return getAdminByUsername(username) != null;
    }

    public static void clear(){
        Admins.clear();
    }

    public static void addAdmin(Admin admin){
        Admins.add(admin);
    }

    public static Admin getAdminByUsername(String username) {
        for (Admin admin : Admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }
        return null;
    }
}
