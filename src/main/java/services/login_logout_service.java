package services;

import library.data.AdminData;
import library.entities.Admin;

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
