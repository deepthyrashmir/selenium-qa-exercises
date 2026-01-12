package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class WebTablesTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void addAndEditUserInWebTable() {
        driver.get("https://demoqa.com/webtables");

        // Click "Add" button safely
        WebElement addBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton"))
        );
        jsClick(addBtn);

        // Fill user form
        fillUserForm(
                "John",
                "Doe",
                "john.doe@test.com",
                "28",
                "50000",
                "QA"
        );

        // Verify user added
        Assert.assertTrue(
                driver.findElement(By.xpath("//div[text()='John']")).isDisplayed(),
                "User was not added to the table"
        );
    }

    private void fillUserForm(String firstName, String lastName, String email,
                              String age, String salary, String department) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")))
                .sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("age")).sendKeys(age);
        driver.findElement(By.id("salary")).sendKeys(salary);
        driver.findElement(By.id("department")).sendKeys(department);

        // Submit button FIX (prevents ElementClickInterceptedException)
        WebElement submitBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("submit"))
        );
        jsClick(submitBtn);
    }

    // ===== Utility Method =====
    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true); arguments[0].click();",
                element
        );
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
