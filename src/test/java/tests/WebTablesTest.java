package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class WebTablesTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/webtables");
    }

    @Test
    public void addAndEditUser() {

        // Step 1: Count rows before
        List<WebElement> rowsBefore = driver.findElements(By.cssSelector(".rt-tr-group"));
        int initialCount = rowsBefore.size();

        // Step 2: Click Add and fill form
        driver.findElement(By.id("addNewRecordButton")).click();
        driver.findElement(By.id("firstName")).sendKeys("Deepthy");
        driver.findElement(By.id("lastName")).sendKeys("Rashmi");
        driver.findElement(By.id("userEmail")).sendKeys("deepthy@test.com");
        driver.findElement(By.id("age")).sendKeys("25");
        driver.findElement(By.id("salary")).sendKeys("30000");
        driver.findElement(By.id("department")).sendKeys("QA");
        driver.findElement(By.id("submit")).click();

        // Step 3: Count rows after
        List<WebElement> rowsAfter = driver.findElements(By.cssSelector(".rt-tr-group"));
        Assert.assertEquals(rowsAfter.size(), initialCount + 1);

        // Step 4: Edit age
        driver.findElement(By.id("edit-record-4")).click();
        WebElement ageField = driver.findElement(By.id("age"));
        ageField.clear();
        ageField.sendKeys("30");
        driver.findElement(By.id("submit")).click();

        // Step 5: Verify updated age
        WebElement updatedAge = driver.findElement(By.xpath("//div[text()='30']"));
        Assert.assertTrue(updatedAge.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
