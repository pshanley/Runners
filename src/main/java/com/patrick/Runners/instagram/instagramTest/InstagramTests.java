package com.patrick.Runners.instagram.instagramTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.patrick.Runners.instagram.Cookies;
import com.patrick.Runners.instagram.GenerateCookies;
import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.instagram.InstagramRequest;
import com.patrick.Runners.instagram.LoadCookies;
import com.patrick.Runners.runner.Runner;

public class InstagramTests {


  @Test
  public void instagramRequest() throws IOException, InterruptedException {
    JSONObject jsonObject = InstagramRequest.makeGetRequest("drewhunter00");
    System.out.println(jsonObject);
  }

  @Test
  public void getInstagramDetails() throws IOException, InterruptedException { // E2E test of making instagram request
    Runner mo = new Runner("mo","farah","gomofarah");
    GetInstagramDetails.getInstagramDetails(mo);
    System.out.println(mo.getFollowersCount());
    assertTrue(mo.getFollowersCount() > 1000);
  }

  @Test
  public void CookieFactoryTest(){ // able to load cookies from files, this kind of assumes that there are
                                   // always files there
    Cookies cookies = LoadCookies.cookieFactory();
    int lengthCSRF = cookies.getCsrftoken().length();
    int lengthSession=cookies.getSessionid().length();
    assertEquals(32,lengthCSRF);
    assertTrue(lengthSession > 10);
  }

  @Test // checks CSRF tokens are different and new token is 32
  public void RunCypress() throws IOException, InterruptedException {
    Cookies beforeCookies = LoadCookies.cookieFactory();
    System.out.println(beforeCookies.getCsrftoken());
    GenerateCookies.RunCypress(); // Running cypress changes the ls -la time of both files,
          // I think it always changes the csrf token each time but may not change the session id
    Cookies afterCookies = LoadCookies.cookieFactory();
    System.out.println(afterCookies.getCsrftoken());
    assertNotEquals(beforeCookies.getCsrftoken(),afterCookies.getCsrftoken());
    assertEquals(32,afterCookies.getCsrftoken().length());
  }

  @Test
  public void RunCypressAndMakeInstagramRequest() throws IOException, InterruptedException {
    Runner mo = new Runner("mo","farah","gomofarah");
    Cookies beforeCookies = LoadCookies.cookieFactory();
    System.out.println(beforeCookies.getCsrftoken());
    GenerateCookies.RunCypress();
    Cookies afterCookies = LoadCookies.cookieFactory();
    System.out.println(afterCookies.getCsrftoken());
    assertNotEquals(beforeCookies.getCsrftoken(),afterCookies.getCsrftoken());
    assertEquals(32,afterCookies.getCsrftoken().length());
    GetInstagramDetails.getInstagramDetails(mo);
    System.out.println(mo.getFollowersCount());
    assertTrue(mo.getFollowersCount() > 1000);
  }





}
