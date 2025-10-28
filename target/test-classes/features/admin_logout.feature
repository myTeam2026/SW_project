Feature: Admin Logout
  As an administrator
  I want to log out
  So that my session is closed securely

  Scenario: Successful logout
    Given the admin is logged in with username "admin1" and password "pass123"
    When the admin logs out
    Then the admin should be logged out
    And any admin action should require re-login

