package jira;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestBase {
  protected WebDriver browser;

  @Parameters({ "browserType" })
  @BeforeTest
  public void openBrowser(String browserType) {

    switch (browserType) {
    case "chrome":
      browser = new ChromeDriver();
      break;
    case "firefox":
      browser = new FirefoxDriver();
      break;
    }

    browser = new ChromeDriver();
    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    browser.get("https://jira.hillel.it/secure/Dashboard.jspa");
  }

  @AfterTest
  public void closeBrowser() {
    browser.quit();
  }
}