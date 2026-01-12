package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class WebTablesTest {

    WebDriver driver;

    @Test
    public void addAndEditUser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://demoqa.com/webtables");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Click Add button
        WebElement addBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton"))
        );
        js.executeScript("arguments[0].click();", addBtn);

        fillUserForm(js, wait);
    }

    private void fillUserForm(JavascriptExecutor js, WebDriverWait wait) {

        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("userEmail")).sendKeys("john.doe@test.com");
        driver.findElement(By.id("age")).sendKeys("28");
        driver.findElement(By.id("salary")).sendKeys("50000");
        driver.findElement(By.id("department")).sendKeys("QA");

        WebElement submitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("submit"))
        );

        js.executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        js.executeScript("arguments[0].click();", submitBtn);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
