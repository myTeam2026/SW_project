package library.data;

import library.entities.Admin;
import java.util.ArrayList;
import java.util.List;

public class AdminData {

    private static final List<Admin> admins = new ArrayList<>();

    static {
        admins.add(new Admin("ayah", "1234"));   // الأدمن الأساسي
        admins.add(new Admin("admin", "pass123")); // حساب إضافي اختياري
    }

    
    public static void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public static void clearAdmins() {
        admins.clear();
    }

    public static Admin getAdminByUsername(String username) {
        for (Admin a : admins) {
            if (a.getUsername().equalsIgnoreCase(username)) {
                return a;
            }
        }
        return null;
    }
}
