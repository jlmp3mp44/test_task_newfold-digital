package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    protected static WebDriver driver;
    private By titleBy = By.linkText("Manage Account");

    public MainPage(WebDriver driver){
        this.driver = driver;
        if (!isPageOpened()) {
            throw new IllegalStateException("This is not the Main Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public boolean isPageOpened() {
        // Check if the "Manage Account" link is present
        return driver.findElement(titleBy).isDisplayed();
    }
}
