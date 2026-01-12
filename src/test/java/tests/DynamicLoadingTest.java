package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class DynamicLoadingTest {

    WebDriver driver;

    @Test
    public void verifyHelloWorldText() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // ✅ Wait ONLY for presence (not clickable)
        WebElement startButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#start button"))
        );

        // ✅ Click using JavaScript (most reliable)
        js.executeScript("arguments[0].click();", startButton);

        // ✅ Wait for Hello World text
        WebElement helloText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("finish"))
        );

        Assert.assertEquals(helloText.getText().trim(), "Hello World!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
