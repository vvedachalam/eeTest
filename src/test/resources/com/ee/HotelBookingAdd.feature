@hotelbooking
@add

Feature: Hotel Booking Management - Add Customer
  In order to book a hotel room
  As a User
  I want to add my details


  Scenario: Save customer details using declarative method

    Given I am in the hotel booking homepage
    When I set all valid details
    And I save details
    And wait until saved
    Then I see all above details saved correctly
    And I see all above details saved correctly in service


  Scenario: Save customer with invalid price using declarative method

    Given I am in the hotel booking homepage
    When I set all valid details other than price
    And I save details
    Then I see the customer details are not saved


  Scenario: Save customer with invalid check-in date using declarative method

    Given I am in the hotel booking homepage
    When I set all valid details other than checkin date
    And I save details
    Then I see the customer details are not saved


  Scenario: Save customer with invalid check-out date using declarative method

    Given I am in the hotel booking homepage
    When I set all valid details other than checkout date
    And I save details
    Then I see the customer details are not saved


  Scenario: Save customer with no first name using declarative method

    Given I am in the hotel booking homepage
    When I set all valid details other than empty firstname
    And I save details
    Then I see the customer details are not saved


  Scenario: Save customer with no surname name using declarative method

    Given I am in the hotel booking homepage
    When I set all valid details other than empty surname
    And I save details
    Then I see the customer details are not saved


# The Price(pence) trailing zero is being truncated while storing and hence failing for 1100.50
  @bug
  Scenario Outline: saving customer details using Imperative method

    Given I am in the hotel booking homepage
    When I set the below details
      | <firstname> | <surname> | <price> | <isDepositPaid> | <checkin> | <checkout> |
    And I save details
    And wait until saved
    Then I see all above details saved correctly
    And I see all above details saved correctly in service

    Examples:
      | firstname | surname | price   | isDepositPaid | checkin    | checkout   |
      | Vijay     | Veda    | 100     | false         | 2017-11-28 | 2017-11-30 |
      | Gary      | Foster  | 1100.50 | true          | 2017-12-23 | 2017-12-25 |


# line 93 'Check-in is later than check-out' will fail as the user details is successfully added to the system
  @bug
  Scenario Outline: Negative testing - save customer details with invalid data using Imperative method

    Given I am in the hotel booking homepage
    When I set the below details
      | <firstname> | <surname> | <price> | <isDepositPaid> | <checkin> | <checkout> |
    And I save details
    And wait for 5 seconds
    Then I see the customer details are not saved
    Examples:
      | firstname | surname | price | isDepositPaid | checkin    | checkout   | description                      |
      |           | Veda    | 100   | true          | 2017-11-28 | 2017-11-30 | When firstname is empty          |
      | Vijay     |         | 100   | true          | 2017-11-28 | 2017-11-30 | When surname is empty            |
      | Vijay     | Veda    |       | false         | 2017-11-28 | 2017-11-30 | When price is empty              |
      | Vijay     | Veda    | xyz   | true          | 2017-11-28 | 2017-11-30 | Invalid price                    |
      | Vijay     | Veda    | 120   | true          |            | 2017-11-30 | When check-in date is empty      |
      | Vijay     | Veda    | 1100  | true          | 2017-12-99 | 2017-11-30 | Invalid check-in date            |
      | Vijay     | Veda    | 1100  | true          | 2017-12-28 | 2017-11-30 | Check-in is later than check-out |
      | Vijay     | Veda    | 120   | true          | 2017-11-28 |            | When check-out date is empty     |
      | Vijay     | Veda    | 100   | true          | 2017-11-25 | 2017-12-99 | Invalid check-out date           |
