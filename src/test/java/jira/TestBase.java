package jira;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestBase {
  protected WebDriver browser;

  @Parameters({ "browserType" })
  @BeforeTest(alwaysRun = true)
  public void openBrowser(String browserType) throws Exception {

    ChromeOptions options = new ChromeOptions();
    // options.addArguments("--headless");
    options.addArguments("--incognito");

    // browser = new RemoteWebDriver(new URL("http://192.168.1.111:9515/"),
    // DesiredCapabilities.chrome());

    switch (browserType) {
    case "chrome":
      browser = new ChromeDriver(options);
      break;
    case "firefox":
      browser = new FirefoxDriver();
      break;
    }

    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  // @BeforeSuite(groups = "Files")
  // public void prepareTestFiles() {
  //   File testFile = new File(Vars.attachmentFileLocation + Vars.attachmentFileName);
  //   if (!(testFile.exists())) {
  //     try {
  //       testFile.createNewFile();
  //     } catch (IOException e) {
  //       System.out.println("Couldn't create a test file!");
  //       e.printStackTrace();
  //     }
  //   }

  // }

  @AfterTest
  public void closeBrowser() throws Exception {
    browser.quit();
  }
}