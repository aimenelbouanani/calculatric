Feature: Calculator
  In order to perform arithmetic operations
  As a user
  I want to be able to sum two numbers

  Scenario: Sum two numbers
    Given I have two numbers 2 and 8
    When I sum them with the calculator
    Then I should receive 10 as the result

