package com.patrick.Runners.instagram;

import java.io.IOException;

import org.json.JSONObject;

import com.patrick.Runners.runner.Runner;

public class GetInstagramDetails {
  // Need followers count and instagram URL
  // only want to make one request, neet to return both fields



  public static void getInstagramDetails(Runner runner) throws IOException, InterruptedException {
    JSONObject instagramResponseJSON = InstagramRequest.makeGetRequest(runner.getInstagramHandle());

    String profilePicUrl = InstagramParsing.returnProfilePicURL(instagramResponseJSON);
    runner.setImageURL(profilePicUrl);

    int followersCount = InstagramParsing.returnFollowerCount(instagramResponseJSON);
    runner.setFollowersCount(followersCount);

  }







}
