package com.patrick.Runners.runner;

public class Runner {
  private String firstName;
  private String lastName;
  private String instagramHandle;

  public Runner() {
  }

  public Runner(String firstName, String lastName, String instagramHandle) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.instagramHandle = instagramHandle;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getInstagramHandle() {
    return instagramHandle;
  }

  public void setInstagramHandle(String instagramHandle) {
    this.instagramHandle = instagramHandle;
  }

}
