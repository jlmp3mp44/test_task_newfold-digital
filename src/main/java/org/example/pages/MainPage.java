package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {
    protected WebDriver driver;
    @FindBy(linkText = "Manage Account")
    private WebElement elementTitle;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        if (!isPageOpened()) {
            throw new IllegalStateException("This is not the Main Page, current page is: " + driver.getCurrentUrl());
        }
    }

    public boolean isPageOpened() {
        return elementTitle.isDisplayed();
    }
}
