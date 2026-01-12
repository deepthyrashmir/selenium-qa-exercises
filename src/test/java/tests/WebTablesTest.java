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
    public void addAndEditUserInWebTable() {

        int rowsBefore = getTableRowCount();

        clickUsingJavaScript(By.id("addNewRecordButton"));

        fillUserForm("Deepthy", "Rashmi",
                "deepthy@test.com", "25", "30000", "QA");

        int rowsAfter = getTableRowCount();
        Assert.assertEquals(rowsAfter, rowsBefore + 1,
                "Row count did not increase after adding user");

        clickUsingJavaScript(By.id("edit-record-4"));

        WebElement ageField = driver.findElement(By.id("age"));
        ageField.clear();
        ageField.sendKeys("30");

        driver.findElement(By.id("submit")).click();

        WebElement updatedAge = driver.findElement(By.xpath("//div[text()='30']"));
        Assert.assertTrue(updatedAge.isDisplayed(),
                "Updated age is not displayed in table");
    }

    private int getTableRowCount() {
        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
        return rows.size();
    }

    private void fillUserForm(String firstName, String lastName,
                              String email, String age,
                              String salary, String department) {

        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("age")).sendKeys(age);
        driver.findElement(By.id("salary")).sendKeys(salary);
        driver.findElement(By.id("department")).sendKeys(department);
        driver.findElement(By.id("submit")).click();
    }

    private void clickUsingJavaScript(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
