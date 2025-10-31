package library.data;

import library.entities.Admin;
import java.util.ArrayList;
import java.util.List;

public class AdminData {
    private static List<Admin> Admins = new ArrayList<>();
    
    static {
        Admins.add(new Admin("admin1", "pass123"));
        Admins.add(new Admin("admin2", "adminpass"));
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