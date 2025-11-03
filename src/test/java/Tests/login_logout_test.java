/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        loginService = new login_logout_service();  // تهيئة السيرفيس
        // إعادة تهيئة الـ Admins
        AdminData.getAdminByUsername("admin1").setLoggedIn(false);
    }

    @Test
    public void testLoginSuccess() {
        boolean result = loginService.login("admin1", "pass123");  // استخدام اسم المتغير الجديد
        assertTrue("Login should succeed with correct credentials", result);

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertTrue(admin.isLoggedIn());
    }

    @Test
    public void testLoginFailure() {
        boolean result = loginService.login("admin1", "wrongpass");  // استخدام اسم المتغير الجديد
        assertFalse("Login should fail with wrong credentials", result);

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertFalse(admin.isLoggedIn());
    }

    @Test
    public void testLogout() {
        loginService.login("admin1", "pass123"); // تسجيل دخول أولاً
        loginService.logout("admin1");

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertFalse(admin.isLoggedIn());
    }
}


