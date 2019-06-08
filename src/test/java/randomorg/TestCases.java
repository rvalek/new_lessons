package randomorg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {
    static WebDriver browser;

    @BeforeTest
    public static void openBrowser() {
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        browser = new ChromeDriver();
    }

    @BeforeMethod
    public static void basePage() {
        browser.get("https://www.random.org/integers/");
    }

    @AfterTest
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public static void generateNumbersSanity() {
        browser.findElement(By.cssSelector("input[value='Get Numbers']")).click();
        String numbers = browser.findElement(By.cssSelector("pre.data")).getText();

        Assert.assertNotEquals(numbers.length(), 0);
    }

    @Test
    public static void optionFields() {
        findAndFill(By.cssSelector("input[name='num']") , "1");
        findAndFill(By.cssSelector("input[name='min']") , "0");
        findAndFill(By.cssSelector("input[name='max']") , "9");
        findAndFill(By.cssSelector("input[name='col']") , "1");



    }

    @Test
    public static void generateOnHomePage() {
        browser.switchTo().frame(browser.findElement(By.cssSelector("iframe")));
        browser.findElement(By.cssSelector("#true-random-integer-generator-button")).click();
    }

    public static WebElement findAndFill(By locator, String text) {
        WebElement e = browser.findElement(locator);
        e.clear();
        e.sendKeys(text);

        return e;
    }

}