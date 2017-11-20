@hotelbooking
@delete

Feature: Hotel Booking Management - Delete Customer
  In order to cancel a hotel room booking
  As a User
  I want to delete my details


  Scenario: delete customer details using declarative method

    Given I am in the hotel booking homepage
    And I set all valid details
    And I save details
    And wait until saved
    When I delete the last customer
    And wait until deleted
    Then I see the customer has been deleted
    And I don't see the deleted item when I call service


  Scenario Outline: delete customers details using imperative method

    Given I am in the hotel booking homepage
    And I set the below details
      | <firstname> | <surname> | <price> | <isDepositPaid> | <checkin> | <checkout> |
    And I save details
    And wait until saved
    When I delete the last customer
    And wait until deleted
    Then I see the customer has been deleted
    And I don't see the deleted item when I call service
    Examples:
      | firstname | surname | price | isDepositPaid | checkin    | checkout   |
      | Vijay     | Veda    | 100   | false         | 2017-11-28 | 2017-11-30 |
      | Gary      | Foster  | 1100  | true          | 2017-12-23 | 2017-12-25 |

