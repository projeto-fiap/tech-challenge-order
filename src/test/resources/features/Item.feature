Feature: Retrieve Items

  Scenario: Retrieve a list of items
    Given a list of valid items exists
    When the items are retrieved
    Then the items should be returned with their details
