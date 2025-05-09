package com.demo.screens;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class Checkout {

    WebDriver p;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "C:/Users/Nada Ayman/Downloads/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        p = new ChromeDriver(options);
        wait = new WebDriverWait(p, Duration.ofSeconds(10));
        p.manage().window().maximize();
    }

    public void loginAndGoToCheckoutStepOne() {
        p.get("https://www.saucedemo.com/v1/index.html");
        p.findElement(By.id("user-name")).sendKeys("standard_user");
        p.findElement(By.id("password")).sendKeys("secret_sauce");
        p.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_primary"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='./cart.html']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_action"))).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }




    // Valid Test Cases

    public static class ValidCheckoutTests extends Checkout {

        @Test(priority = 0)
        public void validCheckoutFlow() {
            loginAndGoToCheckoutStepOne();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("madonna");
            p.findElement(By.id("last-name")).sendKeys("shreif");
            p.findElement(By.id("postal-code")).sendKeys("11311");
            p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();
            wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        }
    }


    // Negative Test Cases
    public static class NegativeCheckoutTests extends Checkout {

        @Test(priority = 1)
        public void missingFirstName() {
            loginAndGoToCheckoutStepOne();

            p.findElement(By.id("last-name")).sendKeys("shreif");
            p.findElement(By.id("postal-code")).sendKeys("11311");
            p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();


        }

        @Test(priority = 2)
        public void missingLastName() {
            loginAndGoToCheckoutStepOne();

            p.findElement(By.id("first-name")).sendKeys("madonna");
            p.findElement(By.id("postal-code")).sendKeys("11311");
            p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();

            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
            Assert.assertTrue(error.getText().contains("Last Name is required"));
        }

        @Test(priority = 3)
        public void missingPostalCode() {
            loginAndGoToCheckoutStepOne();

            p.findElement(By.id("first-name")).sendKeys("madonna");
            p.findElement(By.id("last-name")).sendKeys("shreif");
            p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();

            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
            Assert.assertTrue(error.getText().contains("Postal Code is required"));
        }

        @Test(priority = 4)
        public void AllField() {
            loginAndGoToCheckoutStepOne();

            p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();
        }

        public static class INVALIDCheckoutTests extends Checkout {
            @Test(priority = 5)
            public void invalidPostalCodeFormat() {
                loginAndGoToCheckoutStepOne();

                p.findElement(By.id("first-name")).sendKeys("madonna");
                p.findElement(By.id("last-name")).sendKeys("shreif");
                p.findElement(By.id("postal-code")).sendKeys("abcde");
                p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();

                Assert.assertTrue(p.getCurrentUrl().contains("checkout-step-two"));
            }

            @Test(priority = 6)
            public void invalidFirstName() {
                loginAndGoToCheckoutStepOne();

                p.findElement(By.id("first-name")).sendKeys("123");
                p.findElement(By.id("last-name")).sendKeys("shreif");
                p.findElement(By.id("postal-code")).sendKeys("11311");
                p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();



            }

            @Test(priority = 7)
            public void invalidLasttName() {
                loginAndGoToCheckoutStepOne();

                p.findElement(By.id("first-name")).sendKeys("madonna");
                p.findElement(By.id("last-name")).sendKeys("123");
                p.findElement(By.id("postal-code")).sendKeys("11311");
                p.findElement(By.cssSelector("input.btn_primary.cart_button")).click();

            }
        }
    }
}