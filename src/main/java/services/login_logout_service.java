/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;




import library.data.AdminData;
import library.entities.*;

public class login_logout_service {
  
    public boolean login(String username, String password) {
        Admin admin = AdminData.getAdminByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            admin.setLoggedIn(true);
            return true;
        }
        return false;
    }

    public void logout(String username) {
        Admin admin = AdminData.getAdminByUsername(username);
        if (admin != null) {
            admin.setLoggedIn(false);
        }
    }
}
