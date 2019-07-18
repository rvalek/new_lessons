package jira;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Utils {
  private WebDriver browser;

  public Utils(WebDriver browser) {
    this.browser = browser;
  }

  public void rightClick(WebElement element) {
    new Actions(browser).contextClick(element).build().perform();
  }

  public void scrollDown() {
    JavascriptExecutor jse = (JavascriptExecutor) browser;
    jse.executeScript("window.scrollBy(0,250)", "");
  }

  public WebElement findAndFill(By locator, String text) {
    WebElement e = browser.findElement(locator);
    e.clear();
    e.sendKeys(text);

    return e;
  }

  public File getScreenshot() {
    return ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
  }

  // public saveScreenshot() {
  //   getScreenshot().
  // }

  public static String timeStamp() {
    return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
  }

}
