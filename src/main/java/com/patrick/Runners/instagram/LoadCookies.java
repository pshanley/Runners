package com.patrick.Runners.instagram;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patrick.Runners.SpringContextUtil;

public class LoadCookies {

  private static final Logger LOG = LoggerFactory.getLogger(LoadCookies.class.getName());

  // idk if these file locations will have to go somewhere under target once its running in tomcat
  public static final String filePathDev = "/Users/patrick.hanley/repo/personal/Runners/src/main/resources/cookies/";
  public static final String filePathProd = "/usr/local/runners/cookies/";
  public static final String csrfFileName = "csrf.json";
  public static final String sessionIdFileName = "sessionid.json";



  public static final String drew_hunter = "drewhunter00";

  public static Cookies cookieFactory(){
    String filePath;
    String profile = SpringContextUtil.getApplicationContext().getEnvironment().getActiveProfiles()[0];
    if(profile.equals("dev")){
        filePath = filePathDev;
    }else{
      filePath = filePathProd;
    }

    String sessionid = readSessionId(filePath);
    String csrftoken = readCsrfToken(filePath);
    Cookies cookies = new Cookies(sessionid,csrftoken);
    return cookies;

  }

  public static String readCsrfToken(String filePath) {
    String csrf = null;
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(filePath + csrfFileName)) {
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

  public static String readSessionId(String filePath) {
    String sessionid = null;
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(filePath + sessionIdFileName)) {
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

