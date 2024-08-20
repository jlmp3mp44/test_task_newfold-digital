package org.example.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    protected static WebDriver driver;

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


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void registerUser(String firstName, String lastName,
                                  String address, String city, String postCode,  String country,
                                  String state, String phone,String email, String accountType,
                                  String username, String password) {
        elementFirstName.sendKeys(firstName);
        elementLastName.sendKeys(lastName);
        elementAddress.sendKeys(address);
        elementCity.sendKeys(city);
        elementPostCode.sendKeys(postCode);
        Select selectCountry = new Select(elementCountry);
        selectCountry.selectByValue(country);
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
    }

    public String getUsernameFieldBorderColor() {
        return elementUsername.getCssValue("border-color");
    }

    public MainPage createValidUser(String firstName, String lastName,
                                    String address, String city, String postCode,  String country,
                                    String state, String phone,String email, String accountType,
                                    String username, String password){

        registerUser(firstName, lastName, address, city,  postCode,  country,
                state,  phone,email, accountType, username, password);
        elementSubmit.click();

        return  new MainPage(driver);
    }

    public void createInvalidUser(String firstName, String lastName,
                                  String address, String city, String postCode,  String country,
                                  String state, String phone,String email, String accountType,
                                  String username, String password) throws InterruptedException {
        registerUser(firstName, lastName, address, city,  postCode,  country,
                state,  phone,email, accountType, username, password);
        elementSubmit.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Очікування помилки
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(elementValidateError));
    }
    public boolean isValidateErrorPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Очікування до 10 секунд
        try {
            wait.until(ExpectedConditions.visibilityOf(elementValidateError));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
