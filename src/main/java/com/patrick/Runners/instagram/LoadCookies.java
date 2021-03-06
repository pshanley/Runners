package com.patrick.Runners.instagram;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadCookies {

  private static final Logger LOG = LoggerFactory.getLogger(LoadCookies.class.getName());

  // idk if these file locations will have to go somewhere under target once its running in tomcat
  public static final String csrfFilePath = "/Users/patrick.hanley/repo/personal/Runners/src/main/resources/cookies/csrf.json";
  public static final String sessonIdFilePath = "/Users/patrick.hanley/repo/personal/Runners/src/main/resources/cookies/sessionid.json";
  public static final String drew_hunter = "drewhunter00";

  public static Cookies cookieFactory(){
    String sessionid = readSessionId();
    String csrftoken = readCsrfToken();
    Cookies cookies = new Cookies(sessionid,csrftoken);
    return cookies;

  }

  public static String readCsrfToken() {
    String csrf = null;
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(csrfFilePath)) {
      Object obj = parser.parse(reader);
      JSONObject json = (JSONObject)obj;
      csrf = (String)json.get("csrftoken");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return csrf;
  }

  public static String readSessionId() {
    String sessionid = null;
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(sessonIdFilePath)) {
      Object obj = parser.parse(reader);
      JSONObject json = (JSONObject)obj;
      sessionid = (String)json.get("sessionid");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return sessionid;
  }

}

