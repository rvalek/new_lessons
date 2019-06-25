package jira;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBase {
  protected WebDriver browser;

  @BeforeTest
  public void openBrowser() {
    browser = new ChromeDriver();
    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    browser.get("https://jira.hillel.it/secure/Dashboard.jspa");
  }

  @AfterTest
  public void closeBrowser() {
    browser.quit();
  }
}