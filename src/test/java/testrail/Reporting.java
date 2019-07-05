package testrail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

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

}