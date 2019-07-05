package jira;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

public class IssuePage extends PageBase {
  private String newIssuePath;

  @FindBy(css = "a.attachment-title")
  private WebElement linkAttachment;
  @FindBy(css = "a#create_link")
  private WebElement buttonCreate;
  @FindBy(css = "a.issue-created-key")
  private WebElement linkNewIssue;
  @FindBy(css = "a.issue-created-key")
  private List<WebElement> linksNewIssue;

  public IssuePage(WebDriver browser) {
    super(browser);
  }

  public void createIssue() {
    buttonCreate.click();
    utils.findAndFill(By.cssSelector("input#project-field"), Vars.projectName + "\n");

    new FluentWait<>(browser).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(500))
        .ignoring(InvalidElementStateException.class)
        .until(browser -> utils.findAndFill(By.cssSelector("input#summary"), Vars.newIssueSummary)).submit();

    newIssuePath = linkNewIssue.getAttribute("href");
  }

  public boolean hasNewIssueLinks() {
    return linksNewIssue.size() != 0;
  }

  public void viewIssue() {
    browser.get(newIssuePath);
    Assert.assertTrue(browser.getTitle().contains(Vars.newIssueSummary));
  }

  public void uploadAttachment() {
    browser.findElement(By.cssSelector("input.issue-drop-zone__file"))
        .sendKeys(Vars.attachmentFileLocation + Vars.attachmentFileName);

    WebElement linkAttachment = new FluentWait<>(browser).withTimeout(Duration.ofSeconds(10))
        .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class).until(browser -> linkAttachment);

    Assert.assertEquals(Vars.attachmentFileName, linkAttachment.getText());
  }

  public void downloadAttachment() {
    linkAttachment.click();

    // add wait
    // add assert

  }

}
