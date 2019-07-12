package testrail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.ITestResult;

import jira.Utils;

public class Reporting {
  private static Map<Integer, Integer> testNgToTestRailStatuses = new HashMap<Integer, Integer>() {
    {
      put(1, 1);
      put(2, 5);
      put(3, 2);
    }
  };

  private APIClient client;
  private ArrayList<Integer> openedRuns = new ArrayList<>();

  public Reporting(String url, String username, String password) {
    client = new APIClient(url);
    client.setUser(username);
    client.setPassword(password);
  }

  public Integer startRun(Integer projectId, String runName) throws MalformedURLException, APIException, IOException {
    HashMap<String, Object> data = new HashMap<>();
    data.put("name", runName);
    JSONObject r = (JSONObject) client.sendPost("add_run/" + projectId, data);

    Integer newRunId = (Integer) r.get("id");

    openedRuns.add(newRunId);
    return newRunId;
  }

  public Integer startRun(Integer projectId, String runName, int[] caseIds) throws MalformedURLException, APIException, IOException {
    HashMap<String, Object> data = new HashMap<>();
    data.put("name", runName);
    data.put("case_ids", caseIds);
    JSONObject r = (JSONObject) client.sendPost("add_run/" + projectId, data);

    Integer newRunId = (Integer) r.get("id");

    openedRuns.add(newRunId);
    return newRunId;
  }

  public void closeRun(Integer runId) throws MalformedURLException, APIException, IOException {
    client.sendPost("close_run/" + runId, new HashMap<>());
  }

  public void closeAllStartedRuns() throws MalformedURLException, APIException, IOException {
    for (Integer runId : openedRuns) {
      this.closeRun(runId);
    }
  }

  public void reportResult(Integer runId, Integer caseId, Integer result)
      throws MalformedURLException, APIException, IOException {
    HashMap<String, Object> data = new HashMap<>();
    data.put("status_id", convertResult(result));
    client.sendPost("add_result_for_case/" + runId + "/" + caseId, data);
  }

  public void reportResult(Integer runId, Integer caseId, Integer result, String comment)
      throws MalformedURLException, APIException, IOException {
    HashMap<String, Object> data = new HashMap<>();
    data.put("status_id", convertResult(result));
    data.put("comment", comment);
    client.sendPost("add_result_for_case/" + runId + "/" + caseId, data);
  }

  private static Integer convertResult(Integer testNGResult) {
    if (testNgToTestRailStatuses.containsKey(testNGResult))
      return testNgToTestRailStatuses.get(testNGResult);
    else
      return 4;

    // switch (testNGResult) {
    // case 1:
    // return 1; // Success
    // case 2:
    // return 5; // Failure
    // case 3:
    // return 2; // Skip/Blocked
    // default:
    // return 4; // Retest
    // }
  }

  public static Integer getCaseId(ITestResult result) {
    return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TRProps.class).caseId();
  }

  public static int[] getAllCaseIds(HashSet<ITestResult> results) {
    int[] allCaseIds = new int[results.size()];
    int nextIndex = 0;

    for(ITestResult result : results) {
      allCaseIds[nextIndex++] = getCaseId(result);
    }

    return allCaseIds;
  }

  public static void reportToTestRail(HashSet<ITestResult> results) {
    String baseURL = "https://hillelrob.testrail.io/";
    String projectId = "1";
    String runPrefix = "Jira";
    String username = "rvalek@intersog.com";
    String password = "hillel";

    if (baseURL.isEmpty() || projectId.isEmpty()) {
      System.out.println("TestRail reporting is not configured.");
      return;
    }

    System.out.println("Reporting to " + baseURL);

    Reporting trReport = new Reporting(baseURL, username, password);
    // trReport.setCreds(username, password);

    try {
      Integer runId = trReport.startRun(Integer.parseInt(projectId), runPrefix + " Robert Auto - " + Utils.timeStamp(), getAllCaseIds(results));

      for (ITestResult result : results) {
        try {
          int caseId = getCaseId(result);
          // trReport.reportResult(runId, caseId, result.getStatus());
          trReport.reportResult(runId, caseId, result.getStatus(), result.getThrowable().getLocalizedMessage());
        } catch (Exception e) {
          System.out.println(result.getName() + " - Case ID missing; not reporting to TestRail.");
          e.printStackTrace();
        }
      }

      trReport.closeAllStartedRuns();
      System.out.println("Sent reports successfully.");
    } catch (Exception e) {
      System.out.println("Failed to send report to TestRail.");
      e.printStackTrace();
    }
  }

}