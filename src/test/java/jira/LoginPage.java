package jira;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends PageBase {
  @FindBy(css = "li#user-options")
  private List<WebElement> avatar;

  public LoginPage(WebDriver browser) {
    super(browser);
  }

  public void invalidLogin() {
    login(Vars.username, "forautotests");

    // Assert.assertTrue(browser.findElements(By.cssSelector("div.aui-message-error")).size() > 0);
    Assert.assertTrue(avatar.size() == 0);
  }

  public boolean hasErrorMessage() {
    return browser.findElements(By.cssSelector("div.aui-message-error")).size() > 0;
  }

  public void validLogin() {
    login(Vars.username, "wrongpass");

    Assert.assertTrue(avatar.size() > 0);
    Assert.assertTrue(browser.findElement(By.cssSelector("a#header-details-user-fullname"))
        .getAttribute("data-username") == Vars.username);
  }

  private void login(String username, String password) {
    utils.findAndFill(By.cssSelector("input[name='os_username']"), username);
    utils.findAndFill(By.cssSelector("input[name='os_password']"), password);
    browser.findElement(By.cssSelector("input#aui-button aui-button-primary")).click();
  }

}
