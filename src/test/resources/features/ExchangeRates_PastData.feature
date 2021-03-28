@regression 
Feature: Foreign exchange rates with currency conversion for past data 
	This feature is for validating past foreign exchange rates with currency conversion
  Euro is default base value against which prices are returned.

Background: 
	Given Rates API for Foreign Exchange rates 
	
	#week day -> monday
@sanity @positive 
Scenario: Get all exchange rates for "2021-03-22" 
	When I hit URL with date as "2021-03-23" 
	Then API returns the response with status code as 200 
	And All exchange rates are returned against base "EUR" 
	
#week day -> sunday
@positive 
Scenario: Get all exchange rates for sunday with date "2021-03-21" 
	When I hit URL with date as "2021-03-21" 
	Then API returns the response with status code as 200 
	And All exchange rates are returned against base "EUR" 
	
#week day -> saturday	
@positive 
Scenario: Get all exchange rates for saturday with date "2021-03-20" 
	When I hit URL with date as "2021-03-21" 
	Then API returns the response with status code as 200 
	And All exchange rates are returned against base "EUR" 
	
@positive 
Scenario: Get exchange rate for USD against EUR for "2021-03-23" 
	When I hit URL with query parameter "symbols" as "USD" with date as "2021-03-23" 
	Then API returns the response with status code as 200 
	And  Response should contain rate for "USD" against base "EUR" 
	
@positive 
Scenario: Get all exchange rates against CNY for "2021-03-23" 
	When I hit URL with query parameter "base" as "CNY" with date as "2021-03-23" 
	Then API returns the response with status code as 200 
	And  All exchange rates are returned against base "CNY" 
	
@positive 
Scenario: Get exchange rate for GBP against USD for "2021-03-23" 
	When I hit URL with query parameters "base" as "USD" and "symbols" as "GBP" with date as "2021-03-23" 
	Then API returns the response with status code as 200 
	And Response should contain rate for "GBP" against base "USD" 
	
@negative 
Scenario: Get exchange rate for SSS against EUR for "2021-03-23" 
	When I hit URL with query parameter "symbols" as "SSS" with date as "2021-03-23" 
	Then API returns the response with status code as 400 
	
@negative 
Scenario: Get all exchange rates against ZXZ for "2021-03-23" 
	When I hit URL with query parameter "base" as "ZXZ" with date as "2021-03-23" 
	Then API returns the response with status code as 400 
	
@negative 
Scenario: Get exchange rate for PPP against USD for "2021-03-23" 
	When I hit URL with query parameters "base" as "USD" and "symbols" as "PPP" with date as "2021-03-23" 
	Then API returns the response with status code as 400 
