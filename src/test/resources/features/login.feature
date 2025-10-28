 Feature: Admin Login
  As an admin
  I want to log into the system
  So that I can manage users and books

  Scenario: Successful login with valid credentials
    Given the admin exists with username "admin1" and password "pass123"
    When the admin tries to log in with username "admin1" and password "pass123"
    Then the login should be successful

  Scenario: Failed login with invalid credentials
    Given the admin exists with username "admin1" and password "pass123"
    When the admin tries to log in with username "admin1" and password "wrongpass"
    Then the login should fail

