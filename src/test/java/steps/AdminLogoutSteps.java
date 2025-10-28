/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    package steps;

import library.data.AdminData;
import library.entities.admin;
import org.junit.Assert;
import io.cucumber.java.en.*;

public class AdminLogoutSteps {

    private admin admin;

  

    @When("the admin logs out")
    public void adminLogsOut() {
        admin.setLoggedIn(false);
    }

    @Then("the admin should be logged out")
    public void adminShouldBeLoggedOut() {
        Assert.assertFalse("Admin should not be logged in", admin.isLoggedIn());
    }

    @Then("any admin action should require re-login")
    public void adminActionRequiresRelogin() {
        // نحاكي محاولة تنفيذ عملية إدارية
        boolean canManageUsers = admin.isLoggedIn();
        Assert.assertFalse("Admin must re-login before performing actions", canManageUsers);
    }
}

