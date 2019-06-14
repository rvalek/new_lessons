package jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {
  public static WebElement findAndFill(WebDriver browser, By locator, String text) {
    WebElement e = browser.findElement(locator);
    e.clear();
    e.sendKeys(text);

    return e;
  }

}
