package jira;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import testrail.Reporting;

public class TestBase {
  protected WebDriver browser;
  private Reporting testrailReport;
  private Integer newRunId;

  @Parameters({ "browserType" })
  @BeforeTest
  public void openBrowser(String browserType) throws Exception {
    switch (browserType) {
    case "chrome":
      browser = new ChromeDriver();
      break;
    case "firefox":
      browser = new FirefoxDriver();
      break;
    }

    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    testrailReport = new Reporting(Vars.testRailUrl, Vars.testRailUsername, Vars.testRailPassword);
    newRunId = testrailReport.startRun(Vars.testRailJiraProjectId, "Automation " + Utils.timeStamp());
  }

  @AfterTest
  public void closeBrowser() throws Exception {
    browser.quit();

    testrailReport.closeAllStartedRuns();
  }

  @AfterMethod
  public void reportTestResult(ITestResult currentTestResult) throws Exception {
    testrailReport.reportResult(newRunId, getTestrailId(currentTestResult), currentTestResult.getStatus());
  }

  private Integer getTestrailId(ITestResult currentTestResult) {
    return Integer.parseInt(currentTestResult.getMethod().getDescription().split("\\.")[0]);
  }
}