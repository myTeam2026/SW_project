/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.data;




import library.entities.admin;
import java.util.ArrayList;
import java.util.List;

public class AdminData {
    private static List<admin> admins = new ArrayList<>();

    static {
        // يمكن إضافة admins جاهزين للاختبار
        admins.add(new admin("admin1", "pass123"));
        admins.add(new admin("admin2", "adminpass"));
    }

    public static admin getAdminByUsername(String username) {
        for (admin admin : admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }
        return null;
    }
}

