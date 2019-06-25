package jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage {
  protected WebDriver browser;

  public LoginPage(WebDriver browser) {
    this.browser = browser;
  }

  public void invalidLogin() {
    login(Vars.username, "forautotests");

    // Assert.assertTrue(browser.findElements(By.cssSelector("div.aui-message-error")).size() > 0);
    Assert.assertTrue(browser.findElements(By.cssSelector("li#user-options")).size() == 0);
  }

  public boolean hasErrorMessage() {
    return browser.findElements(By.cssSelector("div.aui-message-error")).size() > 0;
  }

  public void validLogin() {
    login(Vars.username, "wrongpass");

    Assert.assertTrue(browser.findElements(By.cssSelector("li#user-options")).size() > 0);
    Assert.assertTrue(browser.findElement(By.cssSelector("a#header-details-user-fullname"))
        .getAttribute("data-username") == Vars.username);
  }

  private void login(String username, String password) {
    Utils.findAndFill(browser, By.cssSelector("input[name='os_username']"), username);
    Utils.findAndFill(browser, By.cssSelector("input[name='os_password']"), password);
    browser.findElement(By.cssSelector("input#aui-button aui-button-primary")).click();
  }

}
