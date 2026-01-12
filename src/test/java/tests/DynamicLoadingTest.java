package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class DynamicLoadingTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
    }

    @Test
    public void verifyHelloWorldIsDisplayed() throws Exception {

        driver.findElement(By.xpath("//button[text()='Start']")).click();

        WebElement resultText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("finish"))
        );

        Assert.assertEquals(resultText.getText(), "Hello World!",
                "Expected text did not match");

        takeScreenshot("hello_world.png");
    }

    private void takeScreenshot(String fileName) throws Exception {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File folder = new File("screenshots");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File dest = new File(folder, fileName);
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
