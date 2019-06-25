package jira;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

public class IssuePage {
  protected WebDriver browser;
  private String newIssuePath;

  private static String linkCss = "a.attachment-title";

  public IssuePage(WebDriver browser) {
    this.browser = browser;
  }

  public void createIssue() {
    browser.findElement(By.cssSelector("a#create_link")).click();
    Utils.findAndFill(browser, By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");

    new FluentWait<>(browser).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(500))
        .ignoring(InvalidElementStateException.class)
        .until(browser -> Utils.findAndFill(browser, By.cssSelector("input#summary"), Vars.newIssueSummary)).submit();

    newIssuePath = browser.findElement(By.cssSelector("a.issue-created-key")).getAttribute("href");
  }

  public boolean hasNewIssueLinks() {
    List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));

    return linkNewIssues.size() != 0;
  }

  public void viewIssue() {
    browser.get(newIssuePath);
    Assert.assertTrue(browser.getTitle().contains(Vars.newIssueSummary));
  }

  public void uploadAttachment() {
    browser.findElement(By.cssSelector("input.issue-drop-zone__file"))
        .sendKeys(Vars.attachmentFileLocation + Vars.attachmentFileName);

    WebElement linkAttachment = new FluentWait<>(browser).withTimeout(Duration.ofSeconds(10))
        .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
        .until(browser -> browser.findElement(By.cssSelector("a.attachment-title")));

    Assert.assertEquals(Vars.attachmentFileName, linkAttachment.getText());
  }

  public void downloadAttachment() {
    browser.findElement(By.cssSelector("a.attachment-title")).click();

    // add wait
    // add assert

  }

}
