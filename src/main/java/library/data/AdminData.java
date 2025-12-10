package library.data;

import library.entities.Admin;
import java.util.ArrayList;
import java.util.List;

public final class AdminData {

    // اسم المتغير أصبح camelCase لتجنب تحذيرات Sonar
    private static List<Admin> admins = new ArrayList<>();

    static {
        admins.add(new Admin("admin1", "pass123"));
        admins.add(new Admin("admin2", "adminpass"));
    }

    // منع إنشاء أي كائن من هذا الكلاس
    private AdminData(){}

    // إرجاع قائمة الأدمنز
    public static List<Admin> buildList(){
        return admins;
    }

    // عدد الأدمنز
    public static int size(){
        return admins.size();
    }

    // التحقق من وجود اسم مستخدم
    public static boolean exists(String username){
        return getAdminByUsername(username) != null;
    }

    // مسح جميع الأدمنز
    public static void clear(){
        admins.clear();
    }

    // إضافة أدمن جديد
    public static void addAdmin(Admin admin){
        admins.add(admin);
    }

    // البحث عن أدمن حسب اسم المستخدم
    public static Admin getAdminByUsername(String username) {
        for (Admin admin : admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }
        return null;
    }
}
