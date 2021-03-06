package listeners;

import java.util.HashSet;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import testrail.Reporting;

public class TestListener implements ITestListener {

  @Override
  public void onTestStart(ITestResult result) {
    // Removes extra skipped results when failed test is retried
    result.getTestContext().getSkippedTests().removeResult(result.getMethod());
  }

  @Override
  public void onTestSuccess(ITestResult result) {

  }

  @Override
  public void onTestFailure(ITestResult result) {

  }

  @Override
  public void onTestSkipped(ITestResult result) {

  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

  }

  @Override
  public void onStart(ITestContext context) {

  }

  @Override
  public void onFinish(ITestContext context) {
    HashSet<ITestResult> allResults = new HashSet<>();
    allResults.addAll(context.getSkippedTests().getAllResults());
    allResults.addAll(context.getFailedTests().getAllResults());
    allResults.addAll(context.getPassedTests().getAllResults());

    Reporting.reportToTestRail(allResults);
  }
}