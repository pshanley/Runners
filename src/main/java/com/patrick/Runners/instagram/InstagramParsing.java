package com.patrick.Runners.instagram;

import org.json.JSONObject;

public class InstagramParsing {


  public static int returnFollowerCount(JSONObject jsonObject){
    int followerCount = jsonObject.getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_followed_by").getInt("count");
    return followerCount;
  }

  public static String returnProfilePicURL(JSONObject jsonObject){
    String profilePic = jsonObject.getJSONObject("graphql").getJSONObject("user").getString("profile_pic_url_hd");

    return profilePic;
  }

}
