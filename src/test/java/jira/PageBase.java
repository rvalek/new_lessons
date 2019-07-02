package jira;

import org.openqa.selenium.WebDriver;

public abstract class PageBase {
  protected WebDriver browser;
  protected Utils utils;

  protected PageBase(WebDriver browser) {
    this.browser = browser;
    utils = new Utils(browser);
  }
}