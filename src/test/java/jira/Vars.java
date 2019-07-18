package jira;

public interface Vars {
  static String username = "autorob";
  static String newIssueSummary = "Sanity Automation Robert " + Utils.timeStamp();
  static String attachmentFileLocation = "/path/to/folder/";
  static String attachmentFileName = "file.jpg";

  // static ArrayList fileNames =

  static String projectName = "General QA Robert (GQR)";

  static String testRailUrl = "https://autorob.testrail.io/";
  static String testRailUsername = "rvalek@intersog.com";
  static String testRailPassword = "hillel";
  static Integer testRailJiraProjectId = 1;
}