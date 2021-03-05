package com.patrick.Runners.instagram.instagramTest;

import org.json.JSONObject;
import org.junit.Test;

import com.patrick.Runners.instagram.GetInstagramDetails;
import com.patrick.Runners.instagram.InstagramRequest;
import com.patrick.Runners.runner.Runner;

public class InstagramTests {


  @Test
  public void instagramRequest(){
    JSONObject jsonObject = InstagramRequest.makeGetRequest("drewhunter00");
    System.out.println(jsonObject);
  }

  @Test
  public void getInstagramDetails(){
    Runner mo = new Runner("mo","farah","gomofarah");

    GetInstagramDetails.getInstagramDetails(mo);
    System.out.println(mo.getFollowersCount());
    System.out.println(mo.getImageURL());

  }



}
