/**
 * Unit tests for the login and logout functionality for Admin users.
 * <p>
 * This test class verifies that the login/logout service works correctly for:
 * <ul>
 *     <li>Successful login with correct credentials</li>
 *     <li>Failed login with incorrect credentials</li>
 *     <li>Logout functionality</li>
 * </ul>
 * </p>
 * <p>
 * Uses JUnit 4 annotations to setup and run the tests.
 * </p>
 * 
 * Author: Hamsa
 * Version: 1.0
 */
package Tests;

import library.data.AdminData;
import library.entities.Admin;
import services.login_logout_service;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class login_logout_test {

    /** Service responsible for login and logout operations */
    private login_logout_service loginService;

    // -------------------- Setup --------------------
    
    /**
     * Initializes the login/logout service and resets admin login status.
     * <p>
     * This method runs before each test to ensure a clean environment.
     * </p>
     */
    @Before
    public void setUp() {
        loginService = new login_logout_service();  // Initialize service
        // Reset admin login status before each test
        AdminData.getAdminByUsername("admin1").setLoggedIn(false);
    }

    // -------------------- Test Cases --------------------

    /**
     * Tests that an admin can successfully login with correct credentials.
     * <p>
     * Verifies both the returned result and the admin's logged-in status.
     * </p>
     */
    @Test
    public void testLoginSuccess() {
        boolean result = loginService.login("admin1", "pass123");
        assertTrue("Login should succeed with correct credentials", result);

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertTrue("Admin should be logged in after successful login", admin.isLoggedIn());
    }

    /**
     * Tests that login fails when incorrect credentials are provided.
     * <p>
     * Verifies both the returned result and that the admin remains logged out.
     * </p>
     */
    @Test
    public void testLoginFailure() {
        boolean result = loginService.login("admin1", "wrongpass");
        assertFalse("Login should fail with wrong credentials", result);

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertFalse("Admin should remain logged out after failed login", admin.isLoggedIn());
    }

    /**
     * Tests the logout functionality for an admin.
     * <p>
     * Logs in first, then logs out, and checks that the admin is no longer logged in.
     * </p>
     */
    @Test
    public void testLogout() {
        loginService.login("admin1", "pass123"); // Login first
        loginService.logout("admin1");

        Admin admin = AdminData.getAdminByUsername("admin1");
        assertFalse("Admin should be logged out after logout", admin.isLoggedIn());
    }
}
