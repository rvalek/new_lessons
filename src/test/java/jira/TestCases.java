package jira;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases extends TestBase {
  private LoginPage loginPage;
  private IssuePage issuePage;

  @BeforeClass
  public void initPages() {
    loginPage = PageFactory.initElements(browser, LoginPage.class);
    issuePage = PageFactory.initElements(browser, IssuePage.class);
  }

  @Test(priority = -1, groups = { "sanity", "logins" })
  public void invalidLogin() {
    loginPage.invalidLogin();

    Assert.assertTrue(loginPage.hasErrorMessage());
  }

  @Test
  public void validLogin() {
    loginPage.validLogin();
  }

  @Test(dependsOnMethods = { "validLogin" })
  public void createIssue() {
    issuePage.createIssue();

    Assert.assertTrue(issuePage.hasNewIssueLinks());
  }

  @Test(dependsOnMethods = { "createIssue" }, groups = { "Issues" })
  public void viewIssue() {
    issuePage.viewIssue();
  }

  @Test(dependsOnMethods = { "viewIssue" }, groups = { "Issues.Attachments" })
  public void uploadAttachment() {
    issuePage.uploadAttachment();
  }

  @Test(dependsOnMethods = { "uploadAttachment" })
  public void downloadAttachment() {
    issuePage.downloadAttachment();
  }

  // @Test
  // public void addUser() {

  // }

}
