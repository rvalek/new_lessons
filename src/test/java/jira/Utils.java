package jira;

import java.text.SimpleDateFormat;
import java.util.Date;

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

  public static String timeStamp() {
    return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
}

}
