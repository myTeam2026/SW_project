/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.data;




import library.entities.Admin;
import java.util.ArrayList;
import java.util.List;

public class AdminData {
    private static List<Admin> Admins = new ArrayList<>();

    static {
        // يمكن إضافة admins جاهزين للاختبار
        Admins.add(new Admin("admin1", "pass123"));
        Admins.add(new Admin("admin2", "adminpass"));
    }

    public static Admin getAdminByUsername(String username) {
        for (Admin Admin : Admins) {
            if (Admin.getUsername().equalsIgnoreCase(username)) {
                return Admin;
            }
        }
        return null;
    }
}

