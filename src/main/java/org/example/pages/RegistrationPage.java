package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    protected WebDriver driver;

    @FindBy(name = "first_name")
    private WebElement elementFirstName;

    @FindBy(name = "last_name")
    private WebElement elementLastName;

    @FindBy(name = "address")
    private WebElement elementAddress;

    @FindBy(name = "city")
    private WebElement elementCity;

    @FindBy(name = "country")
    private WebElement elementCountry;

    @FindBy(name = "state")
    private WebElement elementState;

    @FindBy(name = "phone")
    private WebElement elementPhone;

    @FindBy(name = "email")
    private WebElement elementEmail;

    @FindBy(name = "username")
    private WebElement elementUsername;

    @FindBy(name = "password")
    private WebElement elementPassword;

    @FindBy(name = "Submit")
    private WebElement elementSubmit;

    @FindBy(name = "post_code")
    private WebElement elementPostCode;

    @FindBy(id = "account_type_personal")
    private WebElement elementPersonalAccountType;

    @FindBy(id = "account_type_business")
    private WebElement elementBusinessAccountType;

    @FindBy(className = "form_validation_error")
    private WebElement elementValidateError;

    @FindBy(linkText = "Registration Form")
    private WebElement elementTitle;

    public void fillRegistrationForm(String firstName, String lastName, String address, String city, String postCode, String country,
                                     String state, String phone, String email, String accountType, String username, String password) {
        elementFirstName.sendKeys(firstName);
        elementLastName.sendKeys(lastName);
        elementAddress.sendKeys(address);
        elementCity.sendKeys(city);
        elementPostCode.sendKeys(postCode);
        elementState.sendKeys(state);
        elementPhone.sendKeys(phone);
        elementEmail.sendKeys(email);
        elementUsername.sendKeys(username);
        elementPassword.sendKeys(password);

        if ("personal".equalsIgnoreCase(accountType)) {
            elementPersonalAccountType.click();
        } else if ("business".equalsIgnoreCase(accountType)) {
            elementBusinessAccountType.click();
        }

        Select selectCountry = new Select(elementCountry);
        if (!selectCountry.getAllSelectedOptions().contains(country)) {
            country = "UA";
        }
        selectCountry.selectByValue(country);
    }

    public MainPage createValidUser(String firstName, String lastName, String address, String city, String postCode, String country,
                                    String state, String phone, String email, String accountType, String username, String password) {

        fillRegistrationForm(firstName, lastName, address, city, postCode, country, state, phone, email, accountType, username, password);
        elementSubmit.click();

        return new MainPage(driver);
    }

    public void createInvalidUser(String firstName, String lastName, String address, String city, String postCode, String country,
                                  String state, String phone, String email, String accountType, String username, String password){
        fillRegistrationForm(firstName, lastName, address, city, postCode, country, state, phone, email, accountType, username, password);
        elementSubmit.click();
    }

    public boolean isErrorPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOf(elementValidateError));
        return errorElement.isDisplayed();
    }

    public boolean isPageOpened() {
        return elementTitle.isDisplayed();
    }

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        if (!isPageOpened()) {
            throw new IllegalStateException("This is not the Registration Page, current page is: " + driver.getCurrentUrl());
        }
        PageFactory.initElements(driver, this);
    }

}
