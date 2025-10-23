/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java.steps;

 


import library.data.AdminData;
import library.entities.admin;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class AdminLoginSteps {

    private admin admin;

    @Given("the admin exists with username {string} and password {string}")
    public void adminExists(String username, String password) {
        admin = AdminData.getAdminByUsername(username);
        Assert.assertNotNull("Admin not found", admin);
    }

    @When("the admin tries to log in with username {string} and password {string}")
    public void adminTriesLogin(String username, String password) {
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            admin.setLoggedIn(true);
        } else {
            admin.setLoggedIn(false);
        }
    }

    @Then("the login should be successful")
    public void loginSuccessful() {
        Assert.assertTrue("Admin should be logged in", admin.isLoggedIn());
    }

    @Then("the login should fail")
    public void loginFail() {
        Assert.assertFalse("Admin should not be logged in", admin.isLoggedIn());
    }
}

