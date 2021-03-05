package com.patrick.Runners.instagram;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstagramConnectionManager {

  private static final Logger LOG = LoggerFactory.getLogger(InstagramConnectionManager.class.getName());

  // idk if these file locations will have to go somewhere under target once its running in tomcat
  public static final String csrfFilePath = "/Users/patrick.hanley/repo/personal/InstagramRunners/src/main/resources/cookies/csrf.json";
  public static final String sessonIdFilePath = "/Users/patrick.hanley/repo/personal/InstagramRunners/src/main/resources/cookies/sessionid.json";
  public static final String drew_hunter = "drewhunter00";
  public static void main(String[] args)  {
  }

  // if I can get Drew Hunter's follower count, connection is good. If not, run cypress and get cookies
  /*public static org.json.JSONObject checkConnection(String instagramHandle) throws IOException, InterruptedException {

    org.json.JSONObject jsonResponse = null;
    // this is making a cookies object from the current JSON files, then actually making a request.
    // But its returning the cookies that they work and then GetFollowerCount makes anotehr Request
    Cookies cookies = cookieFactory(); // makes a cookie object from the current cookie.json files
    try{

      jsonResponse = GetFollowerCount.MakeInstagramRequest(instagramHandle, cookies);
      return  jsonResponse;

    } catch (NullPointerException e){
      LOG.info("Cannot connect to instagram, running cypress.");
      GenerateCookies.RunCypress();
      Cookies newCookies = cookieFactory();
      jsonResponse = GetFollowerCount.MakeInstagramRequest(instagramHandle, newCookies);
      return jsonResponse;
    }

  }*/



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

