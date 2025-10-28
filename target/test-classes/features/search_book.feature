

Feature: Search book
  As a user
  I want to search for a book by title/author/ISBN
  So that I can find it in the library

  Scenario: Search by title
    Given the following book exists
    When the user searches for a book with title "Clean Code"
    Then the search results should include a book with title "Clean Code"

  Scenario: Search by author
    Given the following book exists
    When the user searches for a book with author "Robert C. Martin"
    Then the search results should include a book with author "Robert C. Martin"

  Scenario: Search by ISBN
    Given the following book exists
    When the user searches for a book with ISBN "9780132350884"
    Then the search results should include a book with ISBN "9780132350884"


