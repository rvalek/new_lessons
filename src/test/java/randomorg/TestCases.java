package randomorg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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
    public static void generateOnHomePage() {
        browser.switchTo().frame(browser.findElement(By.cssSelector("iframe")));
        browser.findElement(By.cssSelector("#true-random-integer-generator-button")).click();

        // Add assertion
    }

    @DataProvider
    public static Object[][] dataProvider() {
        return new Object[][] { { 0, 9, 100, 10 }, { 0, 100, 10000, 1 }, };
    }

    @Test(dataProvider = "dataProvider")
    public static void generateNumber(Integer min, Integer max, Integer total, Integer threshold) {
        findAndFill(By.cssSelector("input[name=num]"), total.toString());
        findAndFill(By.cssSelector("input[name=min]"), min.toString());
        findAndFill(By.cssSelector("input[name=max]"), max.toString()).submit();

        String[] data = browser.findElement(By.cssSelector("pre.data")).getText().trim().split("\\s+");
        HashMap<String, Integer> numberCounter = new HashMap<>();

        // int sequenceLength = 3;
        // String temp = "";
        for (String n : data) {
            // temp += n;

            // if(temp.length() == sequenceLength) {
            numberCounter.put(n, numberCounter.containsKey(n) ? numberCounter.get(n) + 1 : 1);
            // temp = "";
            // }
        }

        Collection<Integer> occurances = numberCounter.values();
        Assert.assertTrue(Collections.max(occurances) - Collections.min(occurances) <= (total / 100 * threshold));
    }

    private static WebElement findAndFill(By locator, String text) {
        WebElement e = browser.findElement(locator);
        e.clear();
        e.sendKeys(text);

        return e;
    }

}