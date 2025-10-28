Feature: Add Book
  As an administrator
  I want to add a book with title, author, and ISBN
  So that users can borrow it

  Scenario: Successful addition of a new book
    Given the admin is logged in with username "admin1" and password "pass123"
    When the admin adds a book with title "Clean Code", author "Robert C. Martin", and ISBN "9780132350884"
    Then the book should be added successfully
    And the book should be searchable by title

