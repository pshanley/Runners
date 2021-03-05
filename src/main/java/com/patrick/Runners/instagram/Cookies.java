package com.patrick.Runners.instagram;

import java.io.Serializable;

public class Cookies implements Serializable {

  private String sessionid;
  private String csrftoken;

  public Cookies(){

  }
  public Cookies(String sessionid, String csrftoken) {
    this.sessionid = sessionid;
    this.csrftoken = csrftoken;
  }

  public String getSessionid() {
    return sessionid;
  }

  public void setSessionid(String sessionid) {
    this.sessionid = sessionid;
  }

  public String getCsrftoken() {
    return csrftoken;
  }

  public void setCsrftoken(String csrftoken) {
    this.csrftoken = csrftoken;
  }
}
