# Assignment 5 - Frontend Test

You now have a working REST api for your bank. Make a simple HTML+JS frontend on top of it. It should call your REST api.

Using WebDriver, create tests for your bank to verify the flow.

- Create a simple front-end for your bank.
    - Flow and functionality is important
    - Design and graphics is not important
- Create tests for a chosen browser in your preferred language using Web- Driver
    - Create some positive tests (eg. it should be possible to transfer money)
    - Create some negative tests (eg. it must not be possible to transfer to a non-existing account)

## Content

-   [Utilities](#Utilities)
    -   [Selenium](#Selenium)
    -   [Selenium IDE](#Selenium-IDE)
    -   [Chrome Driver](#Chrome-Driver)
-   [Testing](#Testing)
    -   [setUp](#setUp)
    -   [addBankTest](#addBankTest)
    -   [addExistingBank](#addExistingBank)
    -   [getCustomer](#getCustomer)
    -   [getNonExistingCustomer](#getNonExistingCustomer)
    -   [addMovement](#addMovement)
    -   [addInvalidMovement](#addInvalidMovement)

## Utilities
### Selenium
Selenium is an automated testing framework used to validate web applications across different browsers and platforms to create Test Scripts. Testing done using the Selenium tool is usually referred to as Selenium Testing.

### Selenium IDE
[Selenium IDE, Chrome Extension](https://chrome.google.com/webstore/detail/selenium-ide/mooikfkahbdckldjjndioackbalphokd)

We used this tool to record the navigation flow in the our web application. After making recording we exported it to a `jUnit` files. 

### Chrome Driver
[chromedriver download](https://chromedriver.chromium.org/downloads)

Selenium needs a web driver to open a a browser and navigate the website. We used a `Chrome Driver` which means selenium opens a Chrome browser for the requested site.

## Testing

[FrontendTest.java](../test-client/SeleniumTest/src/test/FrontendTest.java)

### setUp
The `setUp` method is called before the tests are started.
It is used to creates a Chrome `web driver` and makes a request to the rest point resetting the database.

### addBankTest
This test, tries to add a bank with a unused `bankId` and checks if the new bank is added to the list of banks in the web application.

### addExistingBank
This test tries to add a bank with a existing `bankId` and checks is a alert box is prompted, saying `some error`.

### getCustomer
This test, tries to get a customer with a valid `cpr` and checks if the customers name and cpr is shown in the web application.

### getNonExistingCustomer
This test, tries to get a customer with a invalid `cpr` and checks if an alert box is prompted, saying `some error`.

### addMovement
This test, tries to add a movement using an existing `source account` and an existing `target account` and checks is the movement is addd to the list in the web application.

### addInvalidMovement
This test, tries to add a movement using an invalid `source account` and an invalid `target account` and checks if an alert box is prompted, saying `some error`.