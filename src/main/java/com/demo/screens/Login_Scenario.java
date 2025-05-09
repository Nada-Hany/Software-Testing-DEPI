package com.demo.screens;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class Login_Scenario {
    private WebDriver driver;
    private final String BASE_URL = "https://www.saucedemo.com/v1/index.html";

    @BeforeTest
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        System.setProperty("webdriver.gecko.driver", "C:/Users/Nada Ayman/Downloads/chromedriver-win64/chromedriver.exe");

        // Configure browser settings
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Open Saucedemo site
        driver.get(BASE_URL);
    }

    // Helper method to login
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

    // Helper method to handle password save prompts
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

    // Helper method to logout
    private void logout() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();
    }



    @Test
    public  void TestLogin(){
        driver.get("https://www.saucedemo.com/v1/index.html");

        // 2. Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

    }




    @AfterTest
    public void CloseBrowser(){
        driver.quit();
    }
}
