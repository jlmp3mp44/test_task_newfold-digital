package org.example;

import org.example.pages.MainPage;
import org.example.pages.RegistrationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

public class RegistrationTest {

    protected WebDriver driver;
    private RegistrationPage registrationPage;

    @BeforeMethod
    @Step("Setting up WebDriver and opening the registration page")
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\klubn\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.cheapdomains.com.au/register/member_register_test.php");
        registrationPage = new RegistrationPage(driver);
    }

    @AfterMethod
    @Step("Closing the WebDriver")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "validRegistrationData")
    public Object[][] createData() {
        return new Object[][]{
                {"Alla", "Kol", "fr", "ederf", "4562", "UA", "rfrf", "23443", "ewdfwerf", "personal", "3f2g544g", "lwpekdo"},
                {"John", "Doe", "en", "johnD", "7890", "US", "jd@example.com", "54321", "mypassword", "business", "abcd1234", "doejohn"}
        };
    }

    @DataProvider(name = "invalidRegistrationData")
    public Object[][] createInvalidData() {
        return new Object[][]{
                {"", "", "", "", "", "", "", "", "", "", "", ""},
                {"Anna", "", "", "", "", "UA", "", "213123", "", "", "", ""},
                {"Anna", "Fedchun", "street", "Kyiv", "2342", "UA", "", "213123", "", "proffesional", "Anuchka", ""},
                {"Alla", "Kol", "fr", "ederf", "4562", "UA", "rfrf", "23443", "ewdfwerf", "personal", "3g", "lwp"}
        };
    }

    @Test(dataProvider = "validRegistrationData")
    @Step("Create a valid user with the following data: {firstName}, {lastName}, {email}")
    @Description("Test to verify successful registration with valid data")
    public void testRegistration(String firstName, String lastName,
                                 String address, String city, String postCode, String country,
                                 String state, String phone, String email, String accountType,
                                 String username, String password) {
        MainPage mainPage = registrationPage.createValidUser(firstName, lastName, address, city, postCode, country,
                state, phone, email, accountType, username, password);

        Assert.assertTrue(mainPage.isPageOpened(), "Main Page did not open as expected!");
    }

    @Test(dataProvider = "invalidRegistrationData")
    @Step("Create an invalid user with the following data: {firstName}, {lastName}, {email}")
    @Description("Test to verify validation errors with invalid registration data")
    public void testValidationErrors(String firstName, String lastName,
                                     String address, String city, String postCode, String country,
                                     String state, String phone, String email, String accountType,
                                     String username, String password) throws InterruptedException {
        registrationPage.createInvalidUser(firstName, lastName, address, city, postCode, country,
                state, phone, email, accountType, username, password);

        Assert.assertTrue(registrationPage.isErrorPresent(), "Error is not present!");
        registrationPage.acceptAlert();
    }
}


