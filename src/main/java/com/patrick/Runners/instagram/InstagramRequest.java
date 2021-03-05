package com.patrick.Runners.instagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
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



  public static JSONObject makeGetRequest(String instagramHandle) {
    JSONObject jsonObject = null;

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet(instagramUrlPrefix + instagramHandle + instagramUrlSuffix);

    try {
      LOG.info("Making instagram request for " + instagramHandle);
      HttpResponse response = httpClient.execute(request);

      if (response.getStatusLine().getStatusCode() != 200) { //Check for HTTP response code: 200 = success
        throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
      }

      // Get-Capture Complete application/xml body response
      BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
      String output;

      // Simply iterate through XML response and show on console.
      while ((output = br.readLine()) != null) {
        // if the request returns the Login instagram page this will not be a json object an an error will be through
        try{
          jsonObject = new JSONObject(output);
        } catch (JSONException e){
          System.out.println("there was a json exception you were not logged in");

          break;
        }
      }

    } catch (ClientProtocolException e) {
      e.printStackTrace();

    } catch (IOException e) {
      System.out.println("IO Exception IO Exception");
      e.printStackTrace();
    }
    return (jsonObject);
  }

}
