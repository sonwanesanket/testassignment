@regression
Feature: Foreign exchange rates with currency conversion for latest data
  This feature is for validating latest foreign exchange rates with currency conversion
  Euro is default base value against which prices are returned.

Background:
Given Rates API for Foreign Exchange rates

  @sanity @positive
  Scenario: Get all exchange rates for latest date
    When I hit URL with date as "latest"
    Then API returns the response with status code as 200
    And All exchange rates are returned against base "EUR"
    
  @positive
  Scenario: Get all exchange rates for latest and future date
    When I hit URL with latest and future dates
    Then Both apis returns the response with status code as 200
    And  validate future date rates matches current date
    

  @positive
  Scenario: Get exchange rate for USD against EUR
    When I hit URL with query parameter "symbols" as "USD" with date as "latest"
    Then API returns the response with status code as 200
    And Response should contain rate for "USD" against base "EUR"

  @positive
  Scenario: Get all exchange rates against INR
    When I hit URL with query parameter "base" as "INR" with date as "latest"
    Then API returns the response with status code as 200
    And  All exchange rates are returned against base "INR"

  @positive
  Scenario: Get exchange rate for BRL against INR
    When I hit URL with query parameters "base" as "INR" and "symbols" as "BRL" with date as "latest"
    Then API returns the response with status code as 200
    And  Response should contain rate for "BRL" against base "INR"

  @negative
  Scenario: Validate Get all exchange rates with incorrect url 
    When I hit incorrect URL "https://api.ratesapi.io/api/" with date as ""
    Then API returns the response with status code as 400

  @negative
  Scenario: Get exchange rate for US against EUR
    When I hit URL with query parameter "symbols" as "US" with date as "latest"
    Then API returns the response with status code as 400

  @negative
  Scenario: Get all exchange rates against TYT
    When I hit URL with query parameter "base" as "TYT" with date as "latest"
    Then API returns the response with status code as 400

  @negative
  Scenario: Get exchange rate for 123 against USD
    When I hit URL with query parameters "base" as "USD" and "symbols" as "123" with date as "latest"
    Then API returns the response with status code as 400
