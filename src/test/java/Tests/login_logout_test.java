package Tests;

import library.data.AdminData;
import library.entities.Admin;
import services.login_logout_service;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class login_logout_test {

    private login_logout_service loginService;

    @Before
    public void setUp() {
        AdminData.clearAdmins();
        Admin admin = new Admin("admin1", "Admin User", "pass123");
        AdminData.addAdmin(admin);
        loginService = new login_logout_service();
    }

    @Test
    public void testLoginSuccess() {
        boolean result = loginService.login("admin1", "pass123");
        assertTrue(result);

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertTrue(admin.isLoggedIn());
    }

    @Test
    public void testLoginFailure() {
        boolean result = loginService.login("admin1", "wrongpass");
        assertFalse(result);

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertFalse(admin.isLoggedIn());
    }

    @Test
    public void testLogout() {
        loginService.login("admin1", "pass123");
        loginService.logout("admin1");

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertFalse(admin.isLoggedIn());
    }
}
