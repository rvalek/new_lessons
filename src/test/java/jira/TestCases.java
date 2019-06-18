package jira;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {
  private static WebDriver browser;
  private static String username = "autorob";

  private static String newIssueSummary = "Sanity Automation Robert " + Utils.timeStamp();
  private static String newIssuePath;

  @BeforeTest
  public static void openBrowser() {
    browser = new ChromeDriver();
    browser.get("https://jira.hillel.it/secure/Dashboard.jspa");
  }

  @AfterTest
  public static void closeBrowser() {
    browser.quit();
  }

  @Test(priority = -1)
  public static void invalidLogin() {
    login(username, "forautotests");

    Assert.assertTrue(browser.findElements(By.cssSelector("div.aui-message-error")).size() > 0);
    Assert.assertTrue(browser.findElements(By.cssSelector("li#user-options")).size() == 0);
  }

  @Test()
  public static void validLogin(String username, String password) {
    login(username, "wrongpass");

    Assert.assertTrue(browser.findElements(By.cssSelector("li#user-options")).size() > 0);
    Assert.assertTrue(browser.findElement(By.cssSelector("a#header-details-user-fullname"))
        .getAttribute("data-username") == username);
  }

  @Test
  public static void createIssue() {
    browser.findElement(By.cssSelector("a#create_link")).click();
    Utils.findAndFill(browser, By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");

    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
    }

    Utils.findAndFill(browser, By.cssSelector("input#summary"), newIssueSummary + "\n");

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));

    Assert.assertTrue(linkNewIssues.size() != 0);

    newIssuePath = linkNewIssues.get(0).getAttribute("href");
  }

  @Test
  public static void viewIssue() {
    browser.get(newIssuePath);
    Assert.assertTrue(browser.getTitle().contains(newIssueSummary));
  }

  // @Test
  // public static void addUser() {

  // }

  // @Test
  // public static void uploadAttachment() {

  // }

  // @Test
  // public static void downloadAttachment() {

  // }

  private static void login(String username, String password) {
    Utils.findAndFill(browser, By.cssSelector("input[name='os_username']"), username);
    Utils.findAndFill(browser, By.cssSelector("input[name='os_password']"), password);
    browser.findElement(By.cssSelector("input#aui-button aui-button-primary")).click();
  }

}
