package com.happy;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class Scenario_1 {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:/Users/Nada Ayman/Downloads/chromedriver-win64/chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testFullCheckoutFlow() {
        // 1. Open login page
        driver.get("https://www.saucedemo.com/v1/index.html");

        // 2. Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // 3. Add item to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_primary"))).click();

        // 4. Go to cart
        driver.findElement(By.cssSelector("a[href='./cart.html']")).click();

        // 5. Click checkout
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_action"))).click();

        // 6. Fill out user info
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Madonna");
        driver.findElement(By.id("last-name")).sendKeys("Shreif");
        driver.findElement(By.id("postal-code")).sendKeys("11311");

        // 7. Continue to overview
        driver.findElement(By.cssSelector("input.btn_primary.cart_button")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));

        // 8. Finish checkout
        driver.findElement(By.className("btn_action")).click();

        // 9. Assert order is complete
        WebElement confirmationHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        Assert.assertEquals(confirmationHeader.getText(), "THANK YOU FOR YOUR ORDER");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
