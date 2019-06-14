package jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCases {
  static WebDriver browser;

  @BeforeTest
  public static void openBrowser() {
    browser = new ChromeDriver();
    browser.get("https://jira.hillel.it/secure/Dashboard.jspa");
  }

  @AfterTest
  public static void closeBrowser() {
    browser.quit();
  }

  @Parameters({ "autorob", "badpass" })
  @Test(priority = -1)
  public static void invalidLogin(String username, String password) {
    login(username, password);

    Assert.assertTrue(browser.findElements(By.cssSelector("div.aui-message-error")).size() > 0);
    Assert.assertTrue(browser.findElements(By.cssSelector("li#user-options")).size() == 0);
  }

  @Parameters({ "autorob", "badpass" })
  @Test()
  public static void validLogin(String username, String password) {
    login(username, password);

    Assert.assertTrue(browser.findElements(By.cssSelector("li#user-options")).size() > 0);
    Assert.assertTrue(browser.findElement(By.cssSelector("a#header-details-user-fullname"))
        .getAttribute("data-username") == username);
  }

  @Test
  public static void createIssue() {

  }

  @Test
  public static void viewIssue() {

  }

  @Test
  public static void addUser() {

  }

  private static void login(String username, String password) {
    Utils.findAndFill(browser, By.cssSelector("input[name='os_username']"), username);
    Utils.findAndFill(browser, By.cssSelector("input[name='os_password']"), password);
    browser.findElement(By.cssSelector("input#aui-button aui-button-primary")).click();
  }

}
