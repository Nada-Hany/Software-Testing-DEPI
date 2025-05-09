package com.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Scenario {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:/Users/Nada Ayman/Downloads/chromedriver-win64/chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLoginAddToCartCheckoutIntegration() {
        driver.get("https://www.saucedemo.com/v1/index.html");

        // Step 1: Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
//
        // Step 2: Add item to cart
        driver.findElement(By.cssSelector("button.btn_primary.btn_inventory")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));

        // Step 3: Proceed to checkout
        driver.findElement(By.cssSelector("a.btn_action.checkout_button")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

        // Step 4: Enter checkout info
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.cssSelector("input.btn_primary.cart_button")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

        // Step 5: Finish order
        driver.findElement(By.cssSelector("button.btn_action.cart_button")).click();
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
        Assert.assertTrue(driver.getPageSource().contains("THANK YOU FOR YOUR ORDER"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
