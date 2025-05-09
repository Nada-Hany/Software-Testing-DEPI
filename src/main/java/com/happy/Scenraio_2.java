package com.happy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;


public class Scenraio_2 {

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
    @Test
    public void loginAndGoToCartStepOne() {
        p.get("https://www.saucedemo.com/v1/index.html");
        p.findElement(By.id("user-name")).sendKeys("standard_user");
        p.findElement(By.id("password")).sendKeys("secret_sauce");
        p.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_primary"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='./cart.html']"))).click();
    }
    @Test
    public void checkContiuneshoppingButton() {
        p.get("https://www.saucedemo.com/v1/index.html");
        p.findElement(By.id("user-name")).sendKeys("standard_user");
        p.findElement(By.id("password")).sendKeys("secret_sauce");
        p.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn_primary"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='./cart.html']"))).click();
        p.findElement(By.className("btn_secondary")).click();
    }

    @AfterClass
    public void tearDown() {
        if (p != null)
            p.quit();
    }

}
