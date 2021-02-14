package com.patrick.Runners.runner;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Runners")
public class Runner implements Serializable {

  @Id
  private String id;
  private String firstName;
  private String lastName;
  private String instagramHandle;
  private int followersCount;
  private String imageURL;


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

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public int getFollowersCount(){
    return followersCount;
  }

  public void setFollowersCount(int followersCount){
    this.followersCount = followersCount;
  }
}
