package com.patrick.Runners.instagram;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InstagramRequest {
  public static final String instagramUrlPrefix = "https://www.instagram.com/";
  public static final String instagramUrlSuffix = "/?__a=1";

  private static final Logger LOG = LoggerFactory.getLogger(InstagramRequest.class.getName());

  /*public static JSONObject GetInstagramStuff(String instagramHandle) throws IOException, InterruptedException {
    // this is checking the connection with default drew hunter, should just check with whatever it gets passed
    JSONObject response = InstagramConnectionManager.checkConnection(instagramHandle);
    //JSONObject response = makeGetRequest(instagramHandle, cookies);
    return response;
  }*/

  public static HttpClient httpClient = MyHttpClientBuilder.buildHttpClient(LoadCookies.cookieFactory());


  public static JSONObject makeGetRequest(String instagramHandle) throws IOException, InterruptedException {
    JSONObject jsonResponse = null;

    HttpGet httpRequest = new HttpGet(instagramUrlPrefix + instagramHandle + instagramUrlSuffix);

    try {
      System.out.println("Making request without cookies");
       jsonResponse = executeGetRequest(httpClient, httpRequest);

    } catch (JSONException e){
      System.out.println("Login failed, generating cookies now");
      GenerateCookies.RunCypress(); // Runs Cypress to login and log cookies
      Cookies newCookies = LoadCookies.cookieFactory(); // returns a cookie object with fields as CSRF and sessionid
      httpClient = MyHttpClientBuilder.buildHttpClient(newCookies);
      jsonResponse = executeGetRequest(httpClient, httpRequest);
    }
    finally {
      httpRequest.releaseConnection();
    }
    return jsonResponse;
  }



  public static JSONObject executeGetRequest(HttpClient httpClient, HttpGet httpRequest){
    JSONObject jsonObject = null;

    try {
      LOG.info("Making instagram request for " + httpRequest);
      HttpResponse response = httpClient.execute(httpRequest);

      if (response.getStatusLine().getStatusCode() != 200) { //Check for HTTP response code: 200 = success
        //throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        jsonObject = new JSONObject();
        return jsonObject.put("response",response.getStatusLine().getStatusCode());
      }
      BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
      String output;
      while ((output = br.readLine()) != null) {
          jsonObject = new JSONObject(output);
      }

    } catch (ClientProtocolException e) {
      e.printStackTrace();

    } catch (IOException e) {
      System.out.println("IO Exception IO Exception");
      e.printStackTrace();
    }
    System.out.println("Returning json object");
    return (jsonObject);

  }



}
