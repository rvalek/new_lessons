package jira;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {
  private WebDriver browser;

  public Utils(WebDriver browser) {
    this.browser = browser;
  }

  public WebElement findAndFill(By locator, String text) {
    WebElement e = browser.findElement(locator);
    e.clear();
    e.sendKeys(text);

    return e;
  }

  public static String timeStamp() {
    return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
  }

}
