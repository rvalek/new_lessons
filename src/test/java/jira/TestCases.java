package jira;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {
  private static WebDriver browser;
  private static String username = "autorob";

  private static String newIssueSummary = "Sanity Automation Robert " + Utils.timeStamp();
  private static String newIssuePath;

  private static String attachmentFileLocation = "/path/to/folder/";
  private static String attachmentFileName = "file.jpg";

  @BeforeTest
  public static void openBrowser() {
    browser = new ChromeDriver();
    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

  @Test(priority = 1)
  public static void validLogin(String username, String password) {
    login(username, "wrongpass");

    Assert.assertTrue(browser.findElements(By.cssSelector("li#user-options")).size() > 0);
    Assert.assertTrue(browser.findElement(By.cssSelector("a#header-details-user-fullname"))
        .getAttribute("data-username") == username);
  }

  @Test(priority = 2)
  public static void createIssue() {
    browser.findElement(By.cssSelector("a#create_link")).click();
    Utils.findAndFill(browser, By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");

    new FluentWait<>(browser).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(500))
        .ignoring(InvalidElementStateException.class)
        .until(browser -> Utils.findAndFill(browser, By.cssSelector("input#summary"), newIssueSummary)).submit();

    List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));

    Assert.assertTrue(linkNewIssues.size() != 0);

    newIssuePath = linkNewIssues.get(0).getAttribute("href");
  }

  @Test(priority = 3)
  public static void viewIssue() {
    browser.get(newIssuePath);
    Assert.assertTrue(browser.getTitle().contains(newIssueSummary));
  }

  // @Test
  // public static void addUser() {

  // }

  @Test(priority = 4)
  public static void uploadAttachment() {
    browser.findElement(By.cssSelector("input.issue-drop-zone__file"))
        .sendKeys(attachmentFileLocation + attachmentFileName);

    WebElement linkAttachment = new FluentWait<>(browser).withTimeout(Duration.ofSeconds(10))
        .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
        .until(browser -> browser.findElement(By.cssSelector("a.attachment-title")));

    Assert.assertEquals(attachmentFileName, linkAttachment.getText());
  }

  @Test(dependsOnMethods =  {"uploadAttachment"})
  public static void downloadAttachment() {
    browser.findElement(By.cssSelector("a.attachment-title")).click();

    // add wait
    // add assert

  }

  private static void login(String username, String password) {
    Utils.findAndFill(browser, By.cssSelector("input[name='os_username']"), username);
    Utils.findAndFill(browser, By.cssSelector("input[name='os_password']"), password);
    browser.findElement(By.cssSelector("input#aui-button aui-button-primary")).click();
  }

}
