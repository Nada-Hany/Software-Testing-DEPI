package com.demo.screens;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.time.Duration;

public class Login_Fail {
    WebDriver driver;
    WebDriverWait wait;
    private final String BASE_URL = "https://www.saucedemo.com/v1/index.html";


    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:/Users/Nada Ayman/Downloads/chromedriver-win64/chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(this.BASE_URL);
    }
    private void login(String username, String password) {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        // Handle potential password save prompt
        this.handlePasswordSavePrompt();
    }

    private void handlePasswordSavePrompt() {
        // If a JS alert appears, accept it
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {
        }

        // If an HTML modal appears (e.g., "Change your password"), close it
        try {
            WebElement closeBtn = driver.findElement(By.cssSelector(".modal__close"));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
            }
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test(priority = 1)
    public void testErrorUserAddToCart() {
        // 1. Navigate to login page
        driver.get(BASE_URL);

        // 2. Enter "error_user" username
        // 3. Enter valid password
        // 4. Click Login button
        login("error_user", "secret_sauce");

        // Verify login was successful
        assertTrue(driver.findElements(By.className("inventory_item")).size() > 0,
                "Login failed for error_user");

        // 5. Click add to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Verify item was added (button should change to 'Remove')
        WebElement removeButton = driver.findElement(By.id("remove-sauce-labs-backpack"));
        assertTrue(removeButton.isDisplayed(), "Add to cart failed for error_user");
    }
    @Test(priority = 2)
    public void testProblemUserLogoNotClickable() {
        // 1. Navigate to login page
        driver.get(BASE_URL);

        // 2. Enter "problem_user" username
        // 3. Enter valid password
        // 4. Click Login button
        login("problem_user", "secret_sauce");

        // Verify login was successful
        assertTrue(driver.findElements(By.className("inventory_item")).size() > 0,
                "Login failed for problem_user");

        // 5. Click on Sauce Labs logo (should not be clickable)
        WebElement logo = driver.findElement(By.className("app_logo"));

        try {
            logo.click();
            // If click succeeds, verify we didn't navigate away
            assertEquals(driver.getCurrentUrl(), BASE_URL + "inventory.html",
                    "Logo should not be clickable for problem_user");
        } catch (ElementNotInteractableException e) {
            // Expected behavior - logo is not clickable
            assertTrue(true, "Logo is not clickable as expected");
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

}
