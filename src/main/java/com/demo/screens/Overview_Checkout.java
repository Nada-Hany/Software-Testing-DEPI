package com.demo.screens;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class Overview_Checkout {

    WebDriver driver = new ChromeDriver();

    @Test (priority = 0)
    public void check_clickable_finish_btn() {

        System.setProperty("webdriver.gecko.driver", "C:/Users/Nada Ayman/Downloads/chromedriver-win64/chromedriver.exe");

        try{

            // open URL
            driver.get("https://www.saucedemo.com/v1/checkout-step-two.html");

            // get finish btn
            WebElement button = driver.findElement(By.cssSelector(".btn_action.cart_button"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            try {
                wait.until(ExpectedConditions.elementToBeClickable(button));
                System.out.println("Button finish with text '" + button.getText() + "' is clickable.");
            } catch (Exception e) {
                System.out.println("Button finish with text '" + button.getText() + "' is NOT clickable.");
            }
        }catch(Exception e){
            System.out.println("couldn't find desired 'FINISH' button");
        }
    }

    @Test (priority = 0)
    public void check_clickable_cancel_btn() {

        System.setProperty("webdriver.gecko.driver", "C:/Users/Nada Ayman/Downloads/chromedriver-win64/chromedriver.exe");


        try{

            // open URL
            driver.get("https://www.saucedemo.com/v1/checkout-step-two.html");
            // get cancel button
            WebElement button = driver.findElement(By.cssSelector(".cart_cancel_link"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            try {
                wait.until(ExpectedConditions.elementToBeClickable(button));
                System.out.println("Button cancel with text '" + button.getText() + "' is clickable.");
            } catch (Exception e) {
                System.out.println("Button cancel with text '" + button.getText() + "' is NOT clickable.");
            }
        }catch(Exception e){
            System.out.println("couldn't find desired 'CANCEL' button");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
