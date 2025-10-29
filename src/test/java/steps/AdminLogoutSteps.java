package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import library.entities.Admin;
import org.junit.Assert;
import steps.ServiceSteps;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*    package steps;

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



public class AdminLogoutSteps {

    private final ServiceSteps serviceSteps;
    
   public AdminLogoutSteps() {
        this.serviceSteps = new ServiceSteps();
    }
    public AdminLogoutSteps(ServiceSteps serviceSteps) {
        this.serviceSteps = serviceSteps;
    }
 
    @When("the admin logs out")
    public void adminLogsOut() {
        admin admin = serviceSteps.getLoggedAdmin();
        Assert.assertNotNull("Admin should exist before logout", admin);
        admin.setLoggedIn(false);
    }

    @Then("the admin should be logged out")
    public void adminShouldBeLoggedOut() {
        admin admin = serviceSteps.getLoggedAdmin();
        Assert.assertFalse("Admin should not be logged in", admin.isLoggedIn());
    }

    @Then("any admin action should require re-login")
    public void anyAdminActionShouldRequireRelogin() {
        admin admin = serviceSteps.getLoggedAdmin();
        boolean canManageUsers = admin != null && admin.isLoggedIn();
        Assert.assertFalse("Admin must re-login before performing actions", canManageUsers);
    }
    
}*/
public class AdminLogoutSteps {

    private final ServiceSteps serviceSteps;
    public AdminLogoutSteps() {
        // Cucumber يحتاج هذا الـ constructor الفارغ
        this.serviceSteps = new ServiceSteps(); // إنشاء كائن جديد افتراضيًا
    }


    public AdminLogoutSteps(ServiceSteps serviceSteps) {
        this.serviceSteps = serviceSteps;
    }

    @When("the admin logs out")
    public void adminLogsOut() {
        Admin admin = (Admin) serviceSteps.getLoggedAdmin();
        Assert.assertNotNull("Admin should exist before logout", admin);
        admin.setLoggedIn(false);
    }

    @Then("the admin should be logged out")
    public void adminShouldBeLoggedOut() {
        Admin admin = (Admin) serviceSteps.getLoggedAdmin();
        Assert.assertFalse("Admin should not be logged in", admin.isLoggedIn());
    }

    @Then("any admin action should require re-login")
    public void adminActionRequiresRelogin() {
        Admin admin = (Admin) serviceSteps.getLoggedAdmin();
        Assert.assertFalse("Admin must re-login before performing actions", admin.isLoggedIn());
    }
}




